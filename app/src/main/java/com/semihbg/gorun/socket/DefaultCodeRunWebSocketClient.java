package com.semihbg.gorun.socket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DefaultCodeRunWebSocketClient implements CodeRunWebSocketClient {

    private final OkHttpClient client;

    public static final CodeRunWebSocketClient instance;

    static {
        instance=new DefaultCodeRunWebSocketClient();
    }

    public DefaultCodeRunWebSocketClient() {
        this.client = new OkHttpClient();
    }

    @Override
    public CodeRunWebSocketContext connect() {
        Request req=new Request.Builder().url(CODE_RUN_SERVER_URI).build();
        MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener=MessageConsumeCodeRunWebSocketListener.empty();
        WebSocket webSocket=client.newWebSocket(req,messageConsumeCodeRunWebSocketListener);
        return new ListenedRunWebSocketContext(webSocket,messageConsumeCodeRunWebSocketListener);
    }

}
