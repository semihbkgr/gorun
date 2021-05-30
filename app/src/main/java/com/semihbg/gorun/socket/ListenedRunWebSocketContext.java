package com.semihbg.gorun.socket;

import com.semihbg.gorun.message.Message;
import okhttp3.WebSocket;

import java.util.function.Consumer;

public class ListenedRunWebSocketContext extends CodeRunWebSocketContext {

    private final MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener;

    public ListenedRunWebSocketContext(WebSocket webSocket, MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener) {
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener = messageConsumeCodeRunWebSocketListener;
    }

    @Override
    public void addOutputConsumer(Consumer<Message> messageConsumer) {
        messageConsumeCodeRunWebSocketListener.addMessageConsumer(messageConsumer);
    }

}
