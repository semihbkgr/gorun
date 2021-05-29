package com.semihbg.gorun.server.config;

import com.semihbg.gorun.server.service.CodeRunService;
import com.semihbg.gorun.server.session.CodeRunWebSocketSession;
import io.netty.buffer.ByteBufAllocator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class CustomWebSocketHandler implements WebSocketHandler {

    private final CodeRunService codeRunService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        CodeRunWebSocketSession codeRunWebSocketSession=new CodeRunWebSocketSession(codeRunService);
        NettyDataBufferFactory factory=new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .filter(i->{
                    System.out.println(i);
                    return true;
                })
                .flatMap(codeRunWebSocketSession::executeCommand)
                .map(session::textMessage)
                .flatMap(i-> {
                    System.out.println("-------------------------");
                    System.out.println(i);
                    System.out.println("-------------------------");
                    return session.send(Mono.just(i));
                })
                .then();
    }

}
