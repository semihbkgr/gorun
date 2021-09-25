package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.service.MessageProcessService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class RunWebSocketContext {

    private final RunWebSocketSession session;
    private final MessageProcessService messageProcessService;

    public Flux<Message> processMessage (Message message){
        return messageProcessService.process(session,message);
    }

}
