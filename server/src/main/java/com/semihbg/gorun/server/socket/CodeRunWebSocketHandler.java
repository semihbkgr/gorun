package com.semihbg.gorun.server.socket;

import com.semihbg.gorun.server.service.CodeRunService;
import com.semihbg.gorun.server.session.CodeRunWebSocketSession;
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

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        CodeRunWebSocketSession codeRunWebSocketSession = new CodeRunWebSocketSession(codeRunService);
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(codeRunWebSocketSession::executeCommand)
                .map(session::textMessage)
                .flatMap(i -> session.send(Mono.just(i)))
                .then();
    }

}
