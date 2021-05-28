package com.semihbg.gorun.socket;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.function.Consumer;

public class ListenedRunWebSocketSession extends CodeRunWebSocketSession {

    private final MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener;

    public ListenedRunWebSocketSession(WebSocket webSocket, WebSocketListener webSocketListener) {
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener = MessageConsumeCodeRunWebSocketListener.wrap(webSocketListener);
    }

    public ListenedRunWebSocketSession(WebSocket webSocket){
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener=MessageConsumeCodeRunWebSocketListener.empty();
    }

    @Override
    public void addOutputConsumer(Consumer<String> messageConsumer) {
        messageConsumeCodeRunWebSocketListener.addMessageConsumer(messageConsumer);
    }

}
