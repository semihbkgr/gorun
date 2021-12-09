package com.semihbkgr.gorun.server.config;

import com.semihbkgr.gorun.server.run.RunProperties;
import com.semihbkgr.gorun.server.socket.RunWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

    private final RunWebSocketHandler runWebSocketHandler;
    private final RunProperties runProperties;

    @Bean
    public HandlerMapping handlerMapping() {
        var urlMap = new HashMap<String, RunWebSocketHandler>();
        urlMap.put(runProperties.getUrl(), runWebSocketHandler);
        var handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setUrlMap(urlMap);
        handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return handlerMapping;
    }

    @Bean
    public WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

}
