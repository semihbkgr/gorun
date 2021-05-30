package com.semihbg.gorun.socket;

import android.util.Log;
import com.semihbg.gorun.message.Message;
import com.semihbg.gorun.message.MessageUtils;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MessageConsumeCodeRunWebSocketListener extends WebSocketListener {

    private final String TAG = CodeRunWebSocketClient.class.getName();

    private final List<Consumer<Message>> messageConsumerList;
    private Optional<WebSocketListener> webSocketListenerOptional;

    private MessageConsumeCodeRunWebSocketListener() {
        super();
        messageConsumerList = new ArrayList<>();
        webSocketListenerOptional=Optional.empty();
    }

    public static MessageConsumeCodeRunWebSocketListener empty(){
        return new MessageConsumeCodeRunWebSocketListener();
    }

    public static MessageConsumeCodeRunWebSocketListener wrap(WebSocketListener webSocketListener){
        MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener=new MessageConsumeCodeRunWebSocketListener();
        messageConsumeCodeRunWebSocketListener.webSocketListenerOptional=Optional.of(webSocketListener);
        return messageConsumeCodeRunWebSocketListener;
    }

    public void addMessageConsumer(Consumer<Message> messageConsumer) {
        messageConsumerList.add(messageConsumer);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Log.i(TAG, "onClosed: Session has been closed");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onClosed(webSocket, code, reason));
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Log.i(TAG, "onClosing: Session is closing");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onClosing(webSocket, code, reason));
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        Log.i(TAG, "onFailure: Session has been failed");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onFailure(webSocket,t,response));
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        Log.i(TAG, "onMessage: Session has been received message in String format");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onMessage(webSocket,text));
        Message message= MessageUtils.unmarshall(text);
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        Log.i(TAG, "onMessage: Session has been received message in byte[] format");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onMessage(webSocket,bytes));
        Message message= MessageUtils.unmarshall(bytes.string(StandardCharsets.UTF_8));
        messageConsumerList.forEach(i -> i.accept(message));
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        Log.i(TAG, "onOpen: Session has been opened");
        webSocketListenerOptional.ifPresent(webSocketListener -> webSocketListener.onOpen(webSocket,response));
    }

}
