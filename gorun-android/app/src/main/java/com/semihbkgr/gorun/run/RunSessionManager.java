package com.semihbkgr.gorun.run;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.util.WebSocketListenerWrapper;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public class RunSessionManager implements RunSessionObservable {

    private final RunWebSocketClient client;
    private final Executor executor;
    private final AtomicReference<RunWebSocketSession> sessionReference;
    private final AtomicReference<RunSessionStatus> statusReference;
    private final List<RunSessionObserver> observerList;

    public RunSessionManager(@NonNull RunWebSocketClient client, @NonNull Executor executor) {
        this.client = client;
        this.executor = executor;
        this.sessionReference = new AtomicReference<>();
        this.statusReference = new AtomicReference<>(RunSessionStatus.NO_SESSION);
        this.observerList = new ArrayList<>();
    }

    public synchronized void connect() {
        if (statusReference.get() == RunSessionStatus.HAS_SESSION)
            sessionReference.get().close();
        else if (statusReference.get() == RunSessionStatus.CREATING)
            sessionReference.get().cancel();
        changeStatus(RunSessionStatus.CREATING);
        sessionReference.set(client.connect(new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                changeStatus(RunSessionStatus.NO_SESSION);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                changeStatus(RunSessionStatus.NO_SESSION);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                changeStatus(RunSessionStatus.HAS_SESSION);
            }
        }));
    }

    public synchronized void connect(@Nullable WebSocketListener webSocketListener) {
        if (statusReference.get() == RunSessionStatus.HAS_SESSION)
            sessionReference.get().close();
        else if (statusReference.get() == RunSessionStatus.CREATING)
            sessionReference.get().cancel();
        changeStatus(RunSessionStatus.CREATING);
        sessionReference.set(client.connect(new WebSocketListenerWrapper(webSocketListener) {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                changeStatus(RunSessionStatus.NO_SESSION);
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @org.jetbrains.annotations.Nullable Response response) {
                changeStatus(RunSessionStatus.NO_SESSION);
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                changeStatus(RunSessionStatus.HAS_SESSION);
                super.onOpen(webSocket, response);
            }
        }));
    }

    @Nullable
    public synchronized RunWebSocketSession session() {
        return sessionReference.get();
    }

    public synchronized RunSessionStatus getStatus() {
        return statusReference.get();
    }

    @Override
    public boolean registerObserver(@NonNull RunSessionObserver observer) {
        return observerList.add(Objects.requireNonNull(observer));
    }

    @Override
    public boolean unregisterObserver(@NonNull RunSessionObserver observer) {
        return observerList.remove(Objects.requireNonNull(observer));
    }

    private void changeStatus(@NonNull RunSessionStatus status) {
        if (statusReference.get() == status)
            return;
        statusReference.set(Objects.requireNonNull(status));
        executor.execute(() -> observerList.forEach(o -> o.onStatusChanged(status)));
    }

}
