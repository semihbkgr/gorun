package com.semihbkgr.gorun.server.message;

import com.semihbkgr.gorun.server.code.file.FileService;
import com.semihbkgr.gorun.server.code.file.FilenameGenerator;
import com.semihbkgr.gorun.server.error.CodeExecutionError;
import com.semihbkgr.gorun.server.metric.ServerInfoManager;
import com.semihbkgr.gorun.server.run.DefaultRunContext;
import com.semihbkgr.gorun.server.run.RunContextTimeoutHandler;
import com.semihbkgr.gorun.server.run.RunStatus;
import com.semihbkgr.gorun.server.run.websocket.RunWebSocketSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProcessingServiceImpl implements MessageProcessingService {

    private final FileService fileService;
    private final FilenameGenerator fileNameGenerator;
    private final RunContextTimeoutHandler runContextTimeoutHandler;
    private final ServerInfoManager serverInfoManager;

    @Override
    public Flux<Message> process(RunWebSocketSession session, Message message) {
        switch (message.action) {
            case RUN:
                return processRunAction(session, message);
            case INPUT:
                return processInputAction(session, message);
            case INTERRUPT:
                return processInterruptAction(session);
            default:
                return Flux.error(new IllegalStateException("Unhandled message action, action: " + message.action.name()));
        }
    }

    private Flux<Message> processRunAction(RunWebSocketSession session, Message message) {
        if (!session.hasRunContext() || (session.hasRunContext() && session.getRunContext().status() != RunStatus.EXECUTING)) {
            return fileService
                    .createFile(fileNameGenerator.generate("go"), message.body)
                    .doFirst(() -> log.info("Execution, sessionId: " + session.id + " currentExecutionCount: " + serverInfoManager.increaseCurrentExecutionCount()))
                    .doOnTerminate(() -> log.info("Execution, sessionId: " + session.id + " currentExecutionCount: " + serverInfoManager.decreaseCurrentExecutionCount()))
                    .map(fileName -> {
                        try {
                            var process = new ProcessBuilder()
                                    .command("go", "run", fileName)
                                    .redirectErrorStream(true)
                                    .start();
                            return new DefaultRunContext(process, fileName);
                        } catch (IOException e) {
                            throw new CodeExecutionError(e);
                        }
                    })
                    .map(runContext -> {
                        log.info("Execution, sessionId: " + session.id + " executionCount: " + serverInfoManager.increaseExecutionCount());
                        session.setRunContext(runContext);
                        runContextTimeoutHandler.addContext(runContext);
                        return Message.of(Action.RUN_ACK, String.valueOf(runContext.startTimeMS()));
                    })
                    .concatWith(
                            DataBufferUtils.readInputStream(() -> session.getRunContext().process().getInputStream(), new DefaultDataBufferFactory(), 256)
                                    .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                                    .map(messageBody -> Message.of(Action.OUTPUT, messageBody))
                                    .subscribeOn(Schedulers.boundedElastic())
                            //.concatWith(Mono.just(Message.of(Action.COMPLETED, String.valueOf(System.currentTimeMillis() - session.getRunContext().startTimeMS()))))
                    )
                    .doOnTerminate(() -> {
                        // TODO: 12/10/2021 file service adjustment
                        //fileService.deleteFile(session.getRunContext().filename())
                        session.getRunContext().setStatus(RunStatus.COMPLETED);
                        runContextTimeoutHandler.removeContext(session.getRunContext());
                    });

        } else
            return Flux.just(Message.of(Action.ILLEGAL_ACTION, "This session has  already an on going process"));
    }

    private Flux<Message> processInputAction(RunWebSocketSession session, Message message) {
        if (session.hasRunContext() && session.getRunContext().status() == RunStatus.EXECUTING) {
            return Flux.create(sink -> {
                final var os = session.getRunContext().process().getOutputStream();
                try {
                    var inputData = message.body.concat(System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
                    os.write(inputData);
                    os.flush();
                    sink.next(Message.of(Action.INPUT_ACK, message.body));
                    sink.complete();
                } catch (IOException e) {
                    sink.error(e);
                }
            });
        } else
            return Flux.just(Message.of(Action.ILLEGAL_ACTION, "This session has not any on going process"));
    }

    private Flux<Message> processInterruptAction(RunWebSocketSession session) {
        if (session.hasRunContext() && session.getRunContext().status() == RunStatus.EXECUTING) {
            return Flux.defer(() -> {
                session.getRunContext().process().destroyForcibly();
                session.getRunContext().setStatus(RunStatus.INTERRUPTED);
                return Mono.just(Message.of(Action.INTERRUPTED, "0"));
            });
        } else
            return Flux.just(Message.of(Action.ILLEGAL_ACTION, "This session has not any on going process"));
    }

}
