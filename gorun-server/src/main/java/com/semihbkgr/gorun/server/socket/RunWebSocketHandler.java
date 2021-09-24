package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.component.MessageMarshaller;
import com.semihbkgr.gorun.server.service.CodeRunLogService;
import com.semihbkgr.gorun.server.service.CodeRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class RunWebSocketHandler implements WebSocketHandler {

    private final CodeRunService codeRunService;
    private final CodeRunLogService codeRunLogService;
    private final MessageMarshaller messageMarshaller;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        RunWebSocketSession runWebSocketSession = new RunWebSocketSession(codeRunService, codeRunLogService);
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(messageMarshaller::unmarshall)
                .flatMap(runWebSocketSession::executeCommand)
                .map(messageMarshaller::marshall)
                .map(session::textMessage)
                .flatMap(i -> session.send(Mono.just(i)))
                .then();
    }

}
