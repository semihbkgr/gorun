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
import java.util.function.Consumer;

class MessageWebSocketListener extends WebSocketListener {

    private final String TAG = RunWebSocketClient.class.getName();

    private final MessageMarshaller messageMarshaller;
    private final WebSocketListener webSocketListener;
    private final List<Consumer<Message>> messageConsumerList;

    private MessageWebSocketListener(@NonNull MessageMarshaller messageMarshaller,
                                     @Nullable WebSocketListener webSocketListener) {
        this.messageMarshaller = messageMarshaller;
        this.webSocketListener = webSocketListener;
        this.messageConsumerList = new ArrayList<>();
    }

    private MessageWebSocketListener(@NonNull MessageMarshaller messageMarshaller) {
        this(messageMarshaller, null);
    }

    public static MessageWebSocketListener empty(@NonNull MessageMarshaller messageMarshaller) {
        return new MessageWebSocketListener(messageMarshaller);
    }

    public static MessageWebSocketListener wrap(@NonNull MessageMarshaller messageMarshaller,
                                                @Nullable WebSocketListener webSocketListener) {
        return new MessageWebSocketListener(messageMarshaller, webSocketListener);
    }

    public boolean addMessageConsumer(Consumer<Message> messageConsumer) {
        return messageConsumerList.add(messageConsumer);
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        Log.i(TAG, "onClosed: Session has been closed");
        if (webSocketListener != null)
            webSocketListener.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        Log.i(TAG, "onClosing: Session is closing");
        if (webSocketListener != null)
            webSocketListener.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        Log.i(TAG, "onFailure: Session has been failed");
        if (webSocketListener != null)
            webSocketListener.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Log.i(TAG, "onMessage: Session has been received message in String format");
        if (webSocketListener != null)
            webSocketListener.onMessage(webSocket, text);
        Message message = messageMarshaller.unmarshall(text);
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        Log.i(TAG, "onMessage: Session has been received message in byte[] format");
        if (webSocketListener != null)
            webSocketListener.onMessage(webSocket, bytes);
        Message message = messageMarshaller.unmarshall(bytes.string(StandardCharsets.UTF_8));
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        Log.i(TAG, "onOpen: Session has been opened");
        if (webSocketListener != null)
            webSocketListener.onOpen(webSocket, response);
    }

}
