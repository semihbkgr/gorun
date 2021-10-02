package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.component.ProcessTimeoutHandler;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.DefaultRunContext;
import com.semihbkgr.gorun.server.run.RunStatus;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MessageProcessingServiceImpl implements MessageProcessingService {

    private final FileService fileService;
    private final FileNameGenerator fileNameGenerator;
    private final ProcessTimeoutHandler processTimeoutHandler;

    @Override
    public Flux<Message> process(RunWebSocketSession session, Message message) {
        switch (message.command) {
            case RUN:
                return processRunCommand(session, message);
            case INPUT:
                return processInputCommand(session, message);
            case INTERRUPT:
                return processInterruptCommand(session);
            default:
                return Flux.error(new IllegalStateException("Unhandled message command, command: " + message.command.name()));
        }
    }

    private Flux<Message> processRunCommand(RunWebSocketSession session, Message message) {
        if (session.runContext == null || (session.runContext != null && session.runContext.status() != RunStatus.EXECUTING)) {
            return fileService
                    .createFile(fileNameGenerator.generate("go"), message.body)
                    .flatMapMany(fileName -> {
                        try {
                            var process = new ProcessBuilder()
                                    .command("go", "run", fileName)
                                    .redirectErrorStream(true)
                                    .start();
                            session.runContext = new DefaultRunContext(process,fileName);
                            processTimeoutHandler.addProcess(process,System.currentTimeMillis());
                            return DataBufferUtils.readInputStream(process::getInputStream, new DefaultDataBufferFactory(), 256);
                        } catch (Exception e) {
                            return Flux.error(e);
                        }
                    })
                    .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                    .map(messageBody -> Message.of(Command.OUTPUT, messageBody))
                    .onErrorReturn(Message.of(Command.SYSTEM))
                    .doOnComplete(()->session.runContext.setStatus(RunStatus.COMPLETED))
                    .doOnError(e->session.runContext.setStatus(RunStatus.ERROR))
                    .doOnTerminate(()->{
                        fileService.deleteFile(session.runContext.filename());
                        processTimeoutHandler.removeProcess(session.runContext.process());
                    });
        } else
            return Flux.just(Message.of(Command.WARN, "This session has  already an on going process"));
    }

    private Flux<Message> processInputCommand(RunWebSocketSession session, Message message) {
        if (session.runContext != null && session.runContext.status() == RunStatus.EXECUTING) {
            return Flux.create(sink -> {
                final var fos = session.runContext.process().getOutputStream();
                try {
                    var inputData = message.body.concat(System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
                    fos.write(inputData);
                    fos.flush();
                    sink.next(Message.of(Command.INFO, message.body));
                    sink.complete();
                } catch (IOException e) {
                    sink.error(e);
                }
            });
        } else
            return Flux.just(Message.of(Command.WARN, "This session has not any on going process"));
    }

    private Flux<Message> processInterruptCommand(RunWebSocketSession session) {
        if (session.runContext != null && session.runContext.status() == RunStatus.EXECUTING) {
            return Flux.defer(() -> {
                session.runContext.process().destroy();
                session.runContext.setStatus(RunStatus.INTERRUPTED);
                return Mono.just(Message.of(Command.INFO));
            });
        } else
            return Flux.just(Message.of(Command.WARN, "This session has not any on going process"));
    }

}
