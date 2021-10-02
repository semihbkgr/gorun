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

    public RunWebSocketSessionImpl(@NonNull WebSocket webSocket,
                                   @NonNull MessageWebSocketListener messageWebSocketListener,
                                   @NonNull MessageMarshaller messageMarshaller) {
        this.webSocket = webSocket;
        this.messageWebSocketListener = messageWebSocketListener;
        this.messageMarshaller = messageMarshaller;
    }

    @Override
    public boolean sendMessage(Message message) {
        boolean sent=webSocket.send(messageMarshaller.marshall(message));
        if(sent)
            Log.i(TAG, String.format("sendMessage: Message was enqueued successfully, Command : %s", message.command.name()));
        else
            Log.w(TAG, "sendMessage: Message cannot be enqueued");
        return sent;
    }

    @Override
    public  boolean addMessageConsumer(Consumer<Message> consumer) {
        boolean added=messageWebSocketListener.addMessageConsumer(consumer);
        if(added)
            Log.i(TAG, "Consumer was added to session successfully");
        else
            Log.w(TAG, "addMessageConsumer: Consumer cannot be added");
        return added;
    }

    @Override
    public boolean close() {
        boolean closed=webSocket.close(1000,"App is closing");
        if(closed)
            Log.i(TAG, "close: web socket is gracefully closing");
        else
            Log.w(TAG, "close: web socket cannot be started closing");
        return closed;
    }

}
