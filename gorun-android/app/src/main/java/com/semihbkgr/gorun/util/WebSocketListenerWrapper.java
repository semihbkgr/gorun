package com.semihbkgr.gorun.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public abstract class WebSocketListenerWrapper extends WebSocketListener {

    private final WebSocketListener proxy;

    protected WebSocketListenerWrapper() {
        this(null);
    }

    protected WebSocketListenerWrapper(@Nullable WebSocketListener proxy) {
        this.proxy = proxy;
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        if (proxy != null)
            proxy.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        if (proxy != null)
            proxy.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        if (proxy != null)
            proxy.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        if (proxy != null)
            proxy.onMessage(webSocket, text);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        if (proxy != null)
            proxy.onMessage(webSocket, bytes);
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        if (proxy != null)
            proxy.onOpen(webSocket, response);
    }

}
