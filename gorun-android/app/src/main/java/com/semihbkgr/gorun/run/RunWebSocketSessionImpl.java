package com.semihbkgr.gorun.run;

import android.util.Log;
import androidx.annotation.NonNull;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.message.MessageMarshaller;
import okhttp3.WebSocket;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class RunWebSocketSessionImpl implements RunWebSocketSession {

    private static final String TAG = RunWebSocketSessionImpl.class.getName();

    private final WebSocket webSocket;
    private final MessageWebSocketListener messageWebSocketListener;
    private final MessageMarshaller messageMarshaller;
    private final AtomicBoolean connected;

    public RunWebSocketSessionImpl(@NonNull WebSocket webSocket,
                                   @NonNull MessageWebSocketListener messageWebSocketListener,
                                   @NonNull MessageMarshaller messageMarshaller) {
        this.webSocket = webSocket;
        this.messageWebSocketListener = messageWebSocketListener;
        this.messageMarshaller = messageMarshaller;
        this.connected = new AtomicBoolean(true);
    }

    @Override
    public void sendMessage(Message message) {
        if (connected.get()) {
            Log.i(TAG, String.format("sendMessage: Message is sending, Command : %s", message.command.name()));
            webSocket.send(messageMarshaller.marshall(message));
        } else throw new IllegalArgumentException("Session has already been closed");
    }

    @Override
    public void addMessageConsumer(Consumer<Message> consumer) {
        messageWebSocketListener.addMessageConsumer(consumer);
    }

}
