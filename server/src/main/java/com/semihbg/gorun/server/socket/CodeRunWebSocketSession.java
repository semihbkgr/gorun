package com.semihbg.gorun.server.socket;

import com.semihbg.gorun.server.message.Command;
import com.semihbg.gorun.server.message.Message;
import com.semihbg.gorun.server.service.CodeRunLogService;
import com.semihbg.gorun.server.service.CodeRunService;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

public class CodeRunWebSocketSession {

    private final CodeRunService codeRunService;
    private final CodeRunLogService codeRunLogService;
    private volatile CodeRunContext lastCodeRunContext;

    public CodeRunWebSocketSession(CodeRunService codeRunService, CodeRunLogService codeRunLogService) {
        this.codeRunService = codeRunService;
        this.codeRunLogService = codeRunLogService;
        lastCodeRunContext = null;
    }

    public Flux<Message> executeCommand(Message message) {
        if (message.command == Command.RUN) {
            if (lastCodeRunContext == null || !lastCodeRunContext.isRunning()) {
                lastCodeRunContext = new CodeRunContext(message.body);
                return codeRunService.run(lastCodeRunContext)
                        .doOnComplete(() -> codeRunLogService.log(lastCodeRunContext));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));
            }
        } else if (message.command == Command.INPUT) {
            if (lastCodeRunContext != null && lastCodeRunContext.isRunning()) {
                return codeRunService.execute(() ->
                        lastCodeRunContext.sendInput(message.body)
                ).thenMany(Flux.just(Message.of(Command.INFO, "Input is sent")));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.INTERRUPT) {
            if (lastCodeRunContext != null && lastCodeRunContext.isRunning()) {
                lastCodeRunContext.interrupt();
                return Flux.just(Message.of(Command.INFO, "Interrupted"));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.DISCONNECT) {
            if (lastCodeRunContext != null && lastCodeRunContext.isRunning()) {
                lastCodeRunContext.interrupt();
                return Flux.empty();
            }
        }
        return Flux.just(Message.of(Command.ERROR, "Illegal Command"));
    }

}
