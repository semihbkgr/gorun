package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.run.RunContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketSession;

@RequiredArgsConstructor
public class RunWebSocketSession {

    public final WebSocketSession webSocketSession;
    public volatile RunContext runContext;

}
