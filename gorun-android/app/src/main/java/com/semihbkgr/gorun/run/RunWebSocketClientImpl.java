package com.semihbkgr.gorun.run;

import android.util.Log;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.message.MessageMarshaller;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.atomic.AtomicReference;

public class RunWebSocketClientImpl implements RunWebSocketClient {

    private static final String TAG=RunWebSocketClientImpl.class.getName();

    private final OkHttpClient httpClient;
    private final MessageMarshaller messageMarshaller;
    private final AtomicReference<RunWebSocketSession> runWebSocketSessionAtomicReference;

    public RunWebSocketClientImpl(OkHttpClient httpClient, MessageMarshaller messageMarshaller) {
        this.httpClient = httpClient;
        this.messageMarshaller = messageMarshaller;
        this.runWebSocketSessionAtomicReference=new AtomicReference<>();
    }

    @Override
    public void connect(@Nullable WebSocketListener webSocketListener) {
        if(runWebSocketSessionAtomicReference.get()!=null && runWebSocketSessionAtomicReference.get().connected()){
            Log.w(TAG, "connect: There is already one web socket session available");
            return;
        }
        MessageWebSocketListener messageWebSocketListener;
        if (webSocketListener == null)
            messageWebSocketListener = MessageWebSocketListener.empty(messageMarshaller);
        else
            messageWebSocketListener = MessageWebSocketListener.wrap(messageMarshaller, webSocketListener);
        Request req = new Request.Builder().url(AppConstants.Nets.SERVER_CODE_RUN_URI).build();
        WebSocket webSocket = httpClient.newWebSocket(req, messageWebSocketListener);
        runWebSocketSessionAtomicReference.set(new RunWebSocketSessionImpl(webSocket, messageWebSocketListener, messageMarshaller));
        Log.i(TAG, "connect: web socket session has been created successfully");
    }

    @Override
    public boolean hasSession() {
        return runWebSocketSessionAtomicReference.get()!=null && runWebSocketSessionAtomicReference.get().connected();
    }

    @Override
    public @Nullable RunWebSocketSession session() {
        return runWebSocketSessionAtomicReference.get();
    }

}
