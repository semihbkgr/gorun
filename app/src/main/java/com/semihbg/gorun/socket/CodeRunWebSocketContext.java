package com.semihbg.gorun.socket;

import android.util.Log;
import com.semihbg.gorun.message.Message;
import com.semihbg.gorun.message.MessageUtils;
import okhttp3.WebSocket;

import java.util.function.Consumer;

public abstract class CodeRunWebSocketContext implements WebSocketSession {

    public static final String TAG=CodeRunWebSocketContext.class.getName();

    protected final WebSocket webSocket;
    private boolean connected;

    protected CodeRunWebSocketContext(WebSocket webSocket) {
        this.webSocket = webSocket;
        connected=true;
    }

    @Override
    public void sendMessage(Message message) {
        if(connected){
            Log.i(TAG, String.format("sendMessage: Message is sending, Command : %s",message.command.name()));
            webSocket.send(MessageUtils.marshall(message));
        }else throw new IllegalArgumentException("Session has been closed");
    }

    @Override
    public void disconnect() {
        if(connected){
            connected=false;
            Log.i(TAG, "sendMessage: Sending disconnect command");
        }else throw new IllegalStateException("Session has been closed");
    }

    public abstract void addOutputConsumer(Consumer<Message> messageConsumer);

}
