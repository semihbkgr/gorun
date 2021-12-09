package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.component.ServerInfoManager;
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
    private final ServerInfoManager serverInfoManager;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final RunWebSocketSession runWebSocketSession = new RunWebSocketSession(session.getId(), session);
        final RunWebSocketContext runWebSocketContext = new RunWebSocketContext(runWebSocketSession, messageExecutor);
        return session
                .receive()
                .doFirst(() -> log.info("Connection, sessionId: " + runWebSocketSession.id, " sessionCount: " + serverInfoManager.increaseSessionCount()))
                .doOnTerminate(() -> log.info("Disconnection, sessionId: " + runWebSocketSession.id, " sessionCount: " + serverInfoManager.decreaseSessionCount()))
                .doOnError(Throwable::printStackTrace)
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(m -> log.info("Incoming message: {}", m.replace("\n", "\\")))
                .map(messageMarshaller::unmarshall)
                .flatMap(runWebSocketContext::processMessage)
                .map(messageMarshaller::marshall)
                .doOnNext(m -> log.info("Outgoing message: {}", m.replace("\n", "\\")))
                .map(session::textMessage)
                .flatMap(m -> session.send(Mono.just(m)))
                .then();
    }

}
