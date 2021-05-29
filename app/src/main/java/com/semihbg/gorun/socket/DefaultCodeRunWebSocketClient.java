package com.semihbg.gorun.socket;

import okhttp3.OkHttpClient;
import okhttp3.Request;

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
    public CodeRunWebSocketSession connect() {
        Request req=new Request.Builder().url("ws://192.168.1.8:8080/echo").build();
        MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener=MessageConsumeCodeRunWebSocketListener.empty();
        return new ListenedRunWebSocketSession(client.newWebSocket(req,messageConsumeCodeRunWebSocketListener),messageConsumeCodeRunWebSocketListener);
    }

}
