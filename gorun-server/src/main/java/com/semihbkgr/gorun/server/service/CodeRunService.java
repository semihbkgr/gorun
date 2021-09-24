package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.DefaultRunContext;
import reactor.core.publisher.Flux;

public interface CodeRunService {

    Flux<Message> run(DefaultRunContext defaultRunContext);

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
