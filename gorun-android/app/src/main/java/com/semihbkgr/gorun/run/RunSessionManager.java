package com.semihbkgr.gorun.run;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.util.WebSocketListenerWrapper;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.atomic.AtomicReference;

public class RunSessionManager {

    private final RunWebSocketClient client;
    private final AtomicReference<RunWebSocketSession> sessionReference;
    private final AtomicReference<RunSessionManagerStatus> statusReference;

    public RunSessionManager(RunWebSocketClient client) {
        this.client = client;
        this.sessionReference = new AtomicReference<>();
        this.statusReference = new AtomicReference<>(RunSessionManagerStatus.NO_SESSION);
    }

    public synchronized void connect() {
        updateStatusOnConnect();
        sessionReference.set(client.connect(new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                statusReference.set(RunSessionManagerStatus.NO_SESSION);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                statusReference.set(RunSessionManagerStatus.NO_SESSION);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                statusReference.set(RunSessionManagerStatus.HAS_SESSION);
            }
        }));
    }

    public synchronized void connect(@Nullable WebSocketListener webSocketListener) {
        updateStatusOnConnect();
        sessionReference.set(client.connect(new WebSocketListenerWrapper(webSocketListener) {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                statusReference.set(RunSessionManagerStatus.NO_SESSION);
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @org.jetbrains.annotations.Nullable Response response) {
                statusReference.set(RunSessionManagerStatus.NO_SESSION);
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                statusReference.set(RunSessionManagerStatus.HAS_SESSION);
                super.onOpen(webSocket, response);
            }
        }));
    }

    private synchronized void updateStatusOnConnect() {
        if (statusReference.get() == RunSessionManagerStatus.HAS_SESSION)
            sessionReference.get().close();
        else if (statusReference.get() == RunSessionManagerStatus.CREATING)
            sessionReference.get().cancel();
        statusReference.set(RunSessionManagerStatus.CREATING);
    }

    @Nullable
    public synchronized RunWebSocketSession session() {
        return sessionReference.get();
    }

    public synchronized RunSessionManagerStatus getStatus() {
        return statusReference.get();
    }

}
