package com.semihbkgr.gorun.run;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.message.MessageMarshaller;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MessageWebSocketListener extends WebSocketListener {

    private final String TAG = RunWebSocketClient.class.getName();

    private final MessageMarshaller messageMarshaller;
    private final Optional<WebSocketListener> webSocketListenerOptional;
    private final List<Consumer<Message>> messageConsumerList;

    private MessageWebSocketListener(@NonNull MessageMarshaller messageMarshaller,
                                     @androidx.annotation.Nullable WebSocketListener webSocketListener) {
        this.messageMarshaller = messageMarshaller;
        this.webSocketListenerOptional = Optional.ofNullable(webSocketListener);
        this.messageConsumerList = new ArrayList<>();
    }

    private MessageWebSocketListener(@NonNull MessageMarshaller messageMarshaller) {
        this(messageMarshaller, null);
    }

    public static MessageWebSocketListener empty(@NonNull MessageMarshaller messageMarshaller) {
        return new MessageWebSocketListener(messageMarshaller);
    }

    public static MessageWebSocketListener wrap(@NonNull MessageMarshaller messageMarshaller,
                                                @NonNull WebSocketListener webSocketListener) {
        return new MessageWebSocketListener(messageMarshaller, webSocketListener);
    }

    public void addMessageConsumer(Consumer<Message> messageConsumer) {
        messageConsumerList.add(messageConsumer);
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        Log.i(TAG, "onClosed: Session has been closed");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onClosed(webSocket, code, reason));
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        Log.i(TAG, "onClosing: Session is closing");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onClosing(webSocket, code, reason));
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        Log.i(TAG, "onFailure: Session has been failed");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onFailure(webSocket, t, response));
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Log.i(TAG, "onMessage: Session has been received message in String format");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onMessage(webSocket, text));
        Message message = messageMarshaller.unmarshall(text);
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        Log.i(TAG, "onMessage: Session has been received message in byte[] format");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onMessage(webSocket, bytes));
        Message message = messageMarshaller.unmarshall(bytes.string(StandardCharsets.UTF_8));
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        Log.i(TAG, "onOpen: Session has been opened");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onOpen(webSocket, response));
    }

}
