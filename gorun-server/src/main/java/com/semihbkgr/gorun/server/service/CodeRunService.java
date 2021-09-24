package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.command.Message;
import com.semihbkgr.gorun.server.run.RunContextImpl;
import reactor.core.publisher.Flux;

public interface CodeRunService {

    Flux<Message> run(RunContextImpl runContextImpl);

    Flux<Void> execute(Execution execution);

    @FunctionalInterface
    interface Execution{
        void run() throws Exception;

        default Runnable toRunnable(){
            return ()->{
                try {
                    this.run();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            };
        }

    }

}
