package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.component.MessageMarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class RunWebSocketHandler implements WebSocketHandler {

    private final MessageMarshaller messageMarshaller;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        RunWebSocketSession runWebSocketSession = new RunWebSocketSession();
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(messageMarshaller::unmarshall)
                .flatMap(runWebSocketSession::executeMessage)
                .map(messageMarshaller::marshall)
                .map(session::textMessage)
                .flatMap(i -> session.send(Mono.just(i)))
                .then();
    }

}
