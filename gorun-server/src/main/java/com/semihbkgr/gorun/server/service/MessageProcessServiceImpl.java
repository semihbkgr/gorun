package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.RunStatus;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MessageProcessServiceImpl implements MessageProcessService {

    private final FileService fileService;
    private final FileNameGenerator fileNameGenerator;

    @Override
    public Flux<Message> process(RunWebSocketSession session, Message message) {
        switch (message.command) {
            case RUN:
                return processRunCommand(session, message);
            case INPUT:
                return processInputCommand(session,message);
            case INTERRUPT:
                return processInterruptCommand(session, message);
            default:
                return Flux.error(new IllegalStateException("Unhandled message command, command: " + message.command.name()));
        }
    }

    private Flux<Message> processRunCommand(RunWebSocketSession session, Message message) {
        if (session.runContext == null || (session.runContext != null && session.runContext.status() != RunStatus.EXECUTING)) {
            return fileService.createFile(fileNameGenerator.generate("go"), message.body)
                    .flatMapMany(fileName ->
                            DataBufferUtils.readInputStream(
                                    () -> new ProcessBuilder()
                                            .command("go", "run", fileName)
                                            .redirectErrorStream(true)
                                            .start()
                                            .getInputStream(),
                                    new DefaultDataBufferFactory(), 256))
                    .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                    .map(messageBody -> Message.of(Command.OUTPUT, messageBody))
                    .onErrorReturn(Message.of(Command.ERROR));
        } else
            return Flux.just(Message.of(Command.ERROR, "This session has  already an on going process"));
    }

    private Flux<Message> processInputCommand(RunWebSocketSession session, Message message) {
        if (session.runContext != null && session.runContext.status() == RunStatus.EXECUTING) {
            return DataBufferUtils.write(
                    DataBufferUtils.readInputStream(
                            ()->new ByteArrayInputStream(message.body.getBytes(StandardCharsets.UTF_8)),
                            new DefaultDataBufferFactory(),256),
                    session.runContext.process().getOutputStream()
            )
                    .thenMany(Mono.just(Message.of(Command.INFO)));
        }else
            return Flux.just(Message.of(Command.ERROR, "This session has not any on going process"));
    }

    private Flux<Message> processInterruptCommand(RunWebSocketSession session, Message message) {
        if (session.runContext != null && session.runContext.status() == RunStatus.EXECUTING) {
            return Flux.defer(() -> {
                session.runContext.process().destroy();
                return Mono.just(Message.of(Command.INFO));
            });
        } else
            return Flux.just(Message.of(Command.ERROR, "This session has not any on going process"));
    }

}
