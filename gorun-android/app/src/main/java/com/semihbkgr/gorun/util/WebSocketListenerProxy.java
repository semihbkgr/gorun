package com.semihbkgr.gorun.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public abstract class WebSocketListenerProxy extends WebSocketListener {

    private final WebSocketListener listener;

    protected WebSocketListenerProxy() {
        this(null);
    }

    protected WebSocketListenerProxy(@Nullable WebSocketListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        if (listener != null)
            listener.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        if (listener != null)
            listener.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        if (listener != null)
            listener.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        if (listener != null)
            listener.onMessage(webSocket, text);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        if (listener != null)
            listener.onMessage(webSocket, bytes);
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        if (listener != null)
            listener.onOpen(webSocket, response);
    }

}
