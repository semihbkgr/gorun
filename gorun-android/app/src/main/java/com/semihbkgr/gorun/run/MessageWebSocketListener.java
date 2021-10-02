package com.semihbkgr.gorun.run;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.message.MessageMarshaller;
import com.semihbkgr.gorun.util.WebSocketListenerWrapper;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class MessageWebSocketListener extends WebSocketListenerWrapper {

    private final MessageMarshaller messageMarshaller;
    private final WebSocketListener webSocketListener;
    private final List<Consumer<Message>> messageConsumerList;

    private MessageWebSocketListener(@NonNull MessageMarshaller messageMarshaller,
                                     @Nullable WebSocketListener webSocketListener) {
        super(webSocketListener);
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
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Message message = messageMarshaller.unmarshall(text);
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
        Message message = messageMarshaller.unmarshall(bytes.string(StandardCharsets.UTF_8));
        messageConsumerList.forEach(i -> i.accept(message));
    }

}
