package com.semihbkgr.gorun.server.run;

import com.semihbkgr.gorun.server.command.Command;
import com.semihbkgr.gorun.server.command.Message;
import com.semihbkgr.gorun.server.service.CodeRunLogService;
import com.semihbkgr.gorun.server.service.CodeRunService;
import reactor.core.publisher.Flux;

public class RunWebSocketSession {

    private final CodeRunService codeRunService;
    private final CodeRunLogService codeRunLogService;
    private volatile RunContextImpl lastRunContextImpl;

    public RunWebSocketSession(CodeRunService codeRunService, CodeRunLogService codeRunLogService) {
        this.codeRunService = codeRunService;
        this.codeRunLogService = codeRunLogService;
        lastRunContextImpl = null;
    }

    public Flux<Message> executeCommand(Message message) {
        if (message.command == Command.RUN) {
            if (lastRunContextImpl == null || !lastRunContextImpl.isRunning()) {
                lastRunContextImpl = new RunContextImpl(message.body);
                return codeRunService.run(lastRunContextImpl)
                        .doOnComplete(() -> codeRunLogService.log(lastRunContextImpl));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));
            }
        } else if (message.command == Command.INPUT) {
            if (lastRunContextImpl != null && lastRunContextImpl.isRunning()) {
                return codeRunService.execute(() ->
                        lastRunContextImpl.sendInput(message.body)
                ).thenMany(Flux.just(Message.of(Command.OUTPUT, message.body.concat(System.lineSeparator()))));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.INTERRUPT) {
            if (lastRunContextImpl != null && lastRunContextImpl.isRunning()) {
                lastRunContextImpl.interrupt();
                return Flux.just(Message.of(Command.INFO, "Interrupted"));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.DISCONNECT) {
            if (lastRunContextImpl != null && lastRunContextImpl.isRunning()) {
                lastRunContextImpl.interrupt();
                return Flux.empty();
            }
        }
        return Flux.just(Message.of(Command.ERROR, "Illegal Command"));
    }

}
