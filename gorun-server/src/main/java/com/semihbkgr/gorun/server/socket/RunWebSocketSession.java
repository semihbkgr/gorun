package com.semihbkgr.gorun.server.socket;

import com.semihbkgr.gorun.server.run.RunContext;
import lombok.NonNull;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicReference;

public class RunWebSocketSession {

    public final String id;
    public final WebSocketSession webSocketSession;
    private final AtomicReference<RunContext> runContextReference;

    public RunWebSocketSession(String id, WebSocketSession webSocketSession) {
        this.id = id;
        this.webSocketSession = webSocketSession;
        this.runContextReference = new AtomicReference<>(null);
    }

    public boolean hasRunContext() {
        return runContextReference.get() != null;
    }

    public void setRunContext(@NonNull RunContext runContext){
        runContextReference.set(runContext);
    }

    public RunContext getRunContext() {
        return runContextReference.get();
    }

}
