package com.semihbkgr.gorun.server.run;

import com.semihbkgr.gorun.server.command.Command;
import com.semihbkgr.gorun.server.command.Message;
import com.semihbkgr.gorun.server.service.CodeRunLogService;
import com.semihbkgr.gorun.server.service.CodeRunService;
import reactor.core.publisher.Flux;

public class CodeRunWebSocketSession {

    private final CodeRunService codeRunService;
    private final CodeRunLogService codeRunLogService;
    private volatile CodeRunContextt lastCodeRunContextt;

    public CodeRunWebSocketSession(CodeRunService codeRunService, CodeRunLogService codeRunLogService) {
        this.codeRunService = codeRunService;
        this.codeRunLogService = codeRunLogService;
        lastCodeRunContextt = null;
    }

    public Flux<Message> executeCommand(Message message) {
        if (message.command == Command.RUN) {
            if (lastCodeRunContextt == null || !lastCodeRunContextt.isRunning()) {
                lastCodeRunContextt = new CodeRunContextt(message.body);
                return codeRunService.run(lastCodeRunContextt)
                        .doOnComplete(() -> codeRunLogService.log(lastCodeRunContextt));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));
            }
        } else if (message.command == Command.INPUT) {
            if (lastCodeRunContextt != null && lastCodeRunContextt.isRunning()) {
                return codeRunService.execute(() ->
                        lastCodeRunContextt.sendInput(message.body)
                ).thenMany(Flux.just(Message.of(Command.OUTPUT, message.body.concat(System.lineSeparator()))));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.INTERRUPT) {
            if (lastCodeRunContextt != null && lastCodeRunContextt.isRunning()) {
                lastCodeRunContextt.interrupt();
                return Flux.just(Message.of(Command.INFO, "Interrupted"));
            } else {
                return Flux.just(Message.of(Command.ERROR, "This session has not an on going process"));
            }
        } else if (message.command == Command.DISCONNECT) {
            if (lastCodeRunContextt != null && lastCodeRunContextt.isRunning()) {
                lastCodeRunContextt.interrupt();
                return Flux.empty();
            }
        }
        return Flux.just(Message.of(Command.ERROR, "Illegal Command"));
    }

}
