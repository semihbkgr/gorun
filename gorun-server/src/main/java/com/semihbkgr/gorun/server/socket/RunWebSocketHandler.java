package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.message.MessageMarshaller;
import com.semihbkgr.gorun.server.service.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RunWebSocketHandler implements WebSocketHandler {

    private final MessageProcessingService messageExecutor;
    private final MessageMarshaller messageMarshaller;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final RunWebSocketSession runWebSocketSession = new RunWebSocketSession(session);
        final RunWebSocketContext runWebSocketContext = new RunWebSocketContext(runWebSocketSession, messageExecutor);
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(messageMarshaller::unmarshall)
                .flatMap(runWebSocketContext::processMessage)
                .map(messageMarshaller::marshall)
                .map(session::textMessage)
                .flatMap(i -> session.send(Mono.just(i)))
                .then();
    }

}
