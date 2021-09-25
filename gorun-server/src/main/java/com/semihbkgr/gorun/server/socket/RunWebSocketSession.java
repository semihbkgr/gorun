package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.run.RunContext;
import lombok.Getter;
import lombok.Setter;


public class RunWebSocketSession {

    public volatile RunContext runContext;


        /*if (message.command == Command.RUN) {
            if (lastDefaultRunContext == null || !lastDefaultRunContext.isRunning()) {
                lastDefaultRunContext = new DefaultRunContextt(message.body);
                return codeRunService.run(lastDefaultRunContext)
                        .doOnComplete(() -> codeRunLogService.log(lastDefaultRunContext));
            } else {

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
        return Flux.just(Message.of(Command.ERROR, "Illegal Command"));*/

   /* private Flux<Message> executeCommandRun(String body) {
        if (runContext != null && runContext.getStatus() != RunStatus.EXECUTING) {
            this.runContext=new DefaultRunContext(body);
        } else
            return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));

    }*/


}
