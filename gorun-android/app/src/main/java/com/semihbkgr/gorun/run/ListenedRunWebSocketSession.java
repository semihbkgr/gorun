package com.semihbkgr.gorun.run;

import com.semihbkgr.gorun.message.Message;
import okhttp3.WebSocket;

import java.util.function.Consumer;

public class ListenedRunWebSocketSession extends CodeRunWebSocketSession {

    private final MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener;

    public ListenedRunWebSocketSession(WebSocket webSocket, MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener) {
        super(webSocket);
        this.messageConsumeCodeRunWebSocketListener = messageConsumeCodeRunWebSocketListener;
    }

    @Override
    public void addMessageConsumer(Consumer<Message> messageConsumer) {
        messageConsumeCodeRunWebSocketListener.addMessageConsumer(messageConsumer);
    }

}
