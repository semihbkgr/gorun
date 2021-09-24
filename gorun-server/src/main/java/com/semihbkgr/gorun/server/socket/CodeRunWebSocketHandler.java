package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.component.MessageMarshallComponent;
import com.semihbkgr.gorun.server.run.CodeRunWebSocketSession;
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
public class CodeRunWebSocketHandler implements WebSocketHandler {

    private final CodeRunService codeRunService;
    private final CodeRunLogService codeRunLogService;
    private final MessageMarshallComponent messageMarshallComponent;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        CodeRunWebSocketSession codeRunWebSocketSession =
                new CodeRunWebSocketSession(codeRunService,codeRunLogService);
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(messageMarshallComponent::unmarshall)
                .flatMap(codeRunWebSocketSession::executeCommand)
                .map(messageMarshallComponent::marshall)
                .map(session::textMessage)
                .flatMap(i -> session.send(Mono.just(i)))
                .then();
    }

}
