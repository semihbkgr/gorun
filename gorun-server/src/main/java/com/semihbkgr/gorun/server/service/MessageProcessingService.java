package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import reactor.core.publisher.Flux;

public interface MessageProcessingService {

    Flux<Message> process(RunWebSocketSession session, Message message);

}
