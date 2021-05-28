package com.semihbg.gorun.socket;

import okhttp3.OkHttpClient;

public class DefaultCodeRunWebSocketClient implements CodeRunWebSocketClient {

    private final OkHttpClient client;

    public DefaultCodeRunWebSocketClient() {
        this.client = new OkHttpClient();
    }

    @Override
    public CodeRunWebSocketSession connect() {
        return null;
    }

    @Override
    public void disconnect() {

    }

}
