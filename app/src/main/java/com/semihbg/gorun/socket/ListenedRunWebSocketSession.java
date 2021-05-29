package com.semihbg.gorun.socket;

import com.semihbg.gorun.command.CodeCommandExecutor;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.function.Consumer;

public class ListenedRunWebSocketSession extends CodeRunWebSocketSession implements CodeCommandExecutor {

    private final MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener;

    public ListenedRunWebSocketSession(WebSocket webSocket, MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener) {
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener = messageConsumeCodeRunWebSocketListener;
    }

    public ListenedRunWebSocketSession(WebSocket webSocket, WebSocketListener webSocketListener) {
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener = MessageConsumeCodeRunWebSocketListener.wrap(webSocketListener);
    }

    public ListenedRunWebSocketSession(WebSocket webSocket) {
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener = MessageConsumeCodeRunWebSocketListener.empty();
    }

    @Override
    public void addOutputConsumer(Consumer<String> messageConsumer) {
        messageConsumeCodeRunWebSocketListener.addMessageConsumer(messageConsumer);
    }

}
