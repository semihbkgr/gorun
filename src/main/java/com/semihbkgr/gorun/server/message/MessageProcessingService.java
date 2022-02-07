package com.semihbkgr.gorun.server.message;

import com.semihbkgr.gorun.server.run.websocket.RunWebSocketSession;
import reactor.core.publisher.Flux;

public interface MessageProcessingService {

    Flux<Message> process(RunWebSocketSession session, Message message);

}
