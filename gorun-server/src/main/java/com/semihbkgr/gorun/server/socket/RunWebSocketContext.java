package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.service.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class RunWebSocketContext {

    private final RunWebSocketSession session;
    private final MessageProcessingService messageProcessingService;

    public Flux<Message> processMessage(Message message) {
        return messageProcessingService.process(session, message);
    }

}
