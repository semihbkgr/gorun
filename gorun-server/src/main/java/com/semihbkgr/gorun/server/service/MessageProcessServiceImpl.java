package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.RunStatus;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
            default:
                return Flux.error(new IllegalStateException("Unhandled message command, command: "+message.command.name()));
        }
    }

    private Flux<Message> processRunCommand(RunWebSocketSession session, Message message) {
        if (session.runContext == null || (session.runContext != null && session.runContext.status() != RunStatus.EXECUTING)) {
            return fileService.createFile(fileNameGenerator.generate("go"), message.body)
                    .flatMapMany(fileName ->
                            DataBufferUtils.readInputStream(
                                    () -> new ProcessBuilder("go", "run", fileName)
                                            .start()
                                            .getInputStream(),
                                    new DefaultDataBufferFactory(), 256))
                    .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                    .map(messageBody -> Message.of(Command.OUTPUT, messageBody));
        } else
            return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));
    }


}
