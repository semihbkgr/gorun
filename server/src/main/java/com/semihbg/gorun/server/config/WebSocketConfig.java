package com.semihbg.gorun.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class WebSocketConfig implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return ;
    }

}
