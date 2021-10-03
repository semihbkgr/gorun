package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.message.Action;
import com.semihbkgr.gorun.server.message.Message;
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

    private final MessageMarshaller messageMarshaller;
    private final MessageProcessingService messageExecutor;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final RunWebSocketSession runWebSocketSession = new RunWebSocketSession();
        final RunWebSocketContext runWebSocketContext = new RunWebSocketContext(runWebSocketSession, messageExecutor);

        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(messageMarshaller::unmarshall)
                .doOnNext(message -> log.info("Incoming message: " + message))
                .flatMap(runWebSocketContext::processMessage)
                .map(messageMarshaller::marshall)
                .doOnNext(message -> log.info("Outgoing message: " + message))
                .map(session::textMessage)
                .flatMap(i -> session.send(Mono.just(i)))
                .then();
    }

}
