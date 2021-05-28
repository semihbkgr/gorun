package com.semihbg.gorun.socket;

import okhttp3.WebSocket;

import java.util.function.Consumer;

public abstract class CodeRunWebSocketSession {

    protected final WebSocket webSocket;

    protected CodeRunWebSocketSession(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    void sendCommand(String command){
        webSocket.send(command);
    }

    abstract void addOutputConsumer(Consumer<String> messageConsumer);

}
