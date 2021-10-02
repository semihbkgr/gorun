package com.semihbkgr.gorun.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public abstract class WebSocketListenerWrapper extends WebSocketListener {

    private final WebSocketListener webSocketListener;

    protected WebSocketListenerWrapper() {
        this(null);
    }

    protected WebSocketListenerWrapper(@Nullable WebSocketListener webSocketListener) {
        this.webSocketListener = webSocketListener;
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        if (webSocketListener != null)
            webSocketListener.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        if (webSocketListener != null)
            webSocketListener.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        if (webSocketListener != null)
            webSocketListener.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        if (webSocketListener != null)
            webSocketListener.onMessage(webSocket, text);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        if (webSocketListener != null)
            webSocketListener.onMessage(webSocket, bytes);
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        if (webSocketListener != null)
            webSocketListener.onOpen(webSocket, response);
    }

}
