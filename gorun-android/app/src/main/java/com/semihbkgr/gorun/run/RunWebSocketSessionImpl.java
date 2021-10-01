package com.semihbkgr.gorun.run;

import android.util.Log;
import androidx.annotation.NonNull;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.message.MessageMarshaller;
import com.semihbkgr.gorun.message.MessageUtils;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class RunWebSocketSessionImpl implements RunWebSocketSession {

    private static final String TAG=RunWebSocketSessionImpl.class.getName();

    private final WebSocket webSocket;
    private final AtomicBoolean connected;
    private final MessageMarshaller messageMarshaller;
    private final MessageWebSocketListener messageWebSocketListener;

    public RunWebSocketSessionImpl(@NonNull WebSocket webSocket, @NonNull MessageMarshaller messageMarshaller) {
        this.webSocket = webSocket;
        this.messageMarshaller = messageMarshaller;
        this.connected = new AtomicBoolean(false);
        this.messageWebSocketListener = MessageWebSocketListener.empty();
    }

    public RunWebSocketSessionImpl(@NonNull WebSocket webSocket, @NonNull MessageMarshaller messageMarshaller, @NonNull WebSocketListener webSocketListener) {
        this.webSocket = webSocket;
        this.messageMarshaller = messageMarshaller;
        this.connected = new AtomicBoolean(false);
        this.messageWebSocketListener = MessageWebSocketListener.wrap(webSocketListener);
    }

    @Override
    public void sendMessage(Message message) {
        if(connected.get()){
            Log.i(TAG, String.format("sendMessage: Message is sending, Command : %s",message.command.name()));
            webSocket.send(MessageUtils.marshall(message));
        }else throw new IllegalArgumentException("Session has already been closed");
    }

    @Override
    public void addMessageConsumer(Consumer<Message> consumer) {

    }

}
