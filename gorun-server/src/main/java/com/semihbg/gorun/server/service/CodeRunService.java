package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.message.Message;
import com.semihbg.gorun.server.socket.CodeRunContext;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicBoolean;

public interface CodeRunService {

    Flux<Message> run(CodeRunContext codeRunContext);

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
