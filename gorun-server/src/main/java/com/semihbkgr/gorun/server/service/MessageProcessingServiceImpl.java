package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.component.RunContextTimeoutHandler;
import com.semihbkgr.gorun.server.error.CodeExecutionError;
import com.semihbkgr.gorun.server.message.Action;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.message.MessageMarshaller;
import com.semihbkgr.gorun.server.run.DefaultRunContext;
import com.semihbkgr.gorun.server.run.RunStatus;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class MessageProcessingServiceImpl implements MessageProcessingService {

    private final FileService fileService;
    private final FileNameGenerator fileNameGenerator;
    private final RunContextTimeoutHandler runContextTimeoutHandler;
    private final MessageMarshaller messageMarshaller;

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

    //;

    private Flux<Message> processRunAction(RunWebSocketSession session, Message message) {
        if (session.runContext == null || (session.runContext != null && session.runContext.status() != RunStatus.EXECUTING)) {
            return fileService
                    .createFile(fileNameGenerator.generate("go"), message.body)
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
                    .map(runContext->{
                        session.runContext=runContext;
                        runContextTimeoutHandler.addContext(runContext);
                        return Message.of(Action.RUN_ACK,String.valueOf(runContext.startTimeMS()));
                    })
                    .concatWith(
                            DataBufferUtils.readInputStream(()->session.runContext.process().getInputStream(), new DefaultDataBufferFactory(), 256)
                                    .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                                    .map(messageBody -> Message.of(Action.OUTPUT, messageBody))
                                    .doOnComplete(() -> session.runContext.setStatus(RunStatus.COMPLETED))

                    )
                    //.doOnError(e -> session.runContext.setStatus(RunStatus.ERROR))
                    .concatWith(
                            Mono.defer(()->fileService.deleteFile(session.runContext.filename()))
                            .then(Mono.defer(()->Mono.just(Message.of(Action.COMPLETED,String.valueOf(System.currentTimeMillis()-session.runContext.startTimeMS()))))
                    )
                    .doOnTerminate(() -> {
                        runContextTimeoutHandler.removeContext(session.runContext);
                    }));
        } else
            return Flux.just(Message.of(Action.ILLEGAL_ACTION, "This session has  already an on going process"));
    }

    private Flux<Message> processInputAction(RunWebSocketSession session, Message message) {
        if (session.runContext != null && session.runContext.status() == RunStatus.EXECUTING) {
            return Flux.create(sink -> {
                final var fos = session.runContext.process().getOutputStream();
                try {
                    var inputData = message.body.concat(System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
                    fos.write(inputData);
                    fos.flush();
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
        if (session.runContext != null && session.runContext.status() == RunStatus.EXECUTING) {
            return Flux.defer(() -> {
                session.runContext.process().destroyForcibly();
                session.runContext.setStatus(RunStatus.INTERRUPTED);
                return Mono.just(Message.of(Action.INTERRUPTED, "0"));
            });
        } else
            return Flux.just(Message.of(Action.ILLEGAL_ACTION, "This session has not any on going process"));
    }

}
