package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.DefaultRunContext;
import com.semihbkgr.gorun.server.service.CodeRunLogService;
import com.semihbkgr.gorun.server.service.CodeRunService;
import reactor.core.publisher.Flux;

public class RunWebSocketSession {

    private final CodeRunService codeRunService;
    private final CodeRunLogService codeRunLogService;
    private volatile DefaultRunContext lastDefaultRunContext;

    public RunWebSocketSession(CodeRunService codeRunService, CodeRunLogService codeRunLogService) {
        this.codeRunService = codeRunService;
        this.codeRunLogService = codeRunLogService;
        lastDefaultRunContext = null;
    }

    public Flux<Message> executeCommand(Message message) {
        if (message.command == Command.RUN) {
            if (lastDefaultRunContext == null || !lastDefaultRunContext.isRunning()) {
                lastDefaultRunContext = new DefaultRunContext(message.body);
                return codeRunService.run(lastDefaultRunContext)
                        .doOnComplete(() -> codeRunLogService.log(lastDefaultRunContext));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));
            }
        } else if (message.command == Command.INPUT) {
            if (lastDefaultRunContext != null && lastDefaultRunContext.isRunning()) {
                return codeRunService.execute(() ->
                        lastDefaultRunContext.sendInput(message.body)
                ).thenMany(Flux.just(Message.of(Command.OUTPUT, message.body.concat(System.lineSeparator()))));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.INTERRUPT) {
            if (lastDefaultRunContext != null && lastDefaultRunContext.isRunning()) {
                lastDefaultRunContext.interrupt();
                return Flux.just(Message.of(Command.INFO, "Interrupted"));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.DISCONNECT) {
            if (lastDefaultRunContext != null && lastDefaultRunContext.isRunning()) {
                lastDefaultRunContext.interrupt();
                return Flux.empty();
            }
        }
        return Flux.just(Message.of(Command.ERROR, "Illegal Command"));
    }

}
