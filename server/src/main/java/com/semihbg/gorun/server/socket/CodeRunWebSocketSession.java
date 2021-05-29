package com.semihbg.gorun.server.socket;

import com.semihbg.gorun.server.message.Command;
import com.semihbg.gorun.server.message.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

public class CodeRunWebSocketSession {

    private AtomicBoolean isRunning;

    public CodeRunWebSocketSession() {
        this.isRunning = new AtomicBoolean(false);
    }

    public Flux<Message> executeCommand(Message message){
        if(message.command==){

        }
    }

}
