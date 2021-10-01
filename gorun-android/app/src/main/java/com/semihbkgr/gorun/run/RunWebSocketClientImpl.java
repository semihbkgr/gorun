package com.semihbkgr.gorun.run;

import androidx.annotation.Nullable;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.message.MessageMarshaller;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class RunWebSocketClientImpl implements RunWebSocketClient {

    private final OkHttpClient httpClient;
    private final MessageMarshaller messageMarshaller;

    public RunWebSocketClientImpl(OkHttpClient httpClient, MessageMarshaller messageMarshaller) {
        this.httpClient = httpClient;
        this.messageMarshaller = messageMarshaller;
    }

    @Override
    public RunWebSocketSession connect(@Nullable WebSocketListener webSocketListener) {
        Request req = new Request.Builder().url(AppConstants.Nets.SERVER_CODE_RUN_URI).build();
        MessageWebSocketListener messageWebSocketListener;
        if (webSocketListener == null)
            messageWebSocketListener = MessageWebSocketListener.empty(messageMarshaller);
        else if (webSocketListener instanceof MessageWebSocketListener)
            messageWebSocketListener = (MessageWebSocketListener) webSocketListener;
        else
            messageWebSocketListener = MessageWebSocketListener.wrap(messageMarshaller, webSocketListener);
        WebSocket webSocket = httpClient.newWebSocket(req, messageWebSocketListener);
        return new RunWebSocketSessionImpl(webSocket, messageWebSocketListener, messageMarshaller);
    }

}
