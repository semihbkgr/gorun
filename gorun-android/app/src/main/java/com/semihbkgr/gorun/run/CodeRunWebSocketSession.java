package com.semihbkgr.gorun.run;

import android.util.Log;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.message.MessageUtils;
import okhttp3.WebSocket;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class CodeRunWebSocketSession implements WebSocketSession {

    public static final String TAG= CodeRunWebSocketSession.class.getName();

    protected final WebSocket webSocket;
    private final AtomicBoolean connected;

    protected CodeRunWebSocketSession(WebSocket webSocket) {
        this.webSocket = webSocket;
        connected=new AtomicBoolean(true);
    }

    @Override
    public void sendMessage(Message message) {
        if(connected.get()){
            Log.i(TAG, String.format("sendMessage: Message is sending, Command : %s",message.command.name()));
            webSocket.send(MessageUtils.marshall(message));
        }else throw new IllegalArgumentException("Session has already been closed");
    }

    public abstract void addMessageConsumer(Consumer<Message> messageConsumer);

}
