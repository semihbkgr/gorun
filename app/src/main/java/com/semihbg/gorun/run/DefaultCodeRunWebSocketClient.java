package com.semihbg.gorun.run;

import com.semihbg.gorun.AppConstants;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DefaultCodeRunWebSocketClient implements CodeRunWebSocketClient {

    public static final CodeRunWebSocketClient instance;

    static {
        instance = new DefaultCodeRunWebSocketClient();
    }

    @Override
    public CodeRunWebSocketSession connect() {
        Request req = new Request.Builder().url(AppConstants.SERVER_CODE_RUN_URI).build();
        MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener = MessageConsumeCodeRunWebSocketListener.empty();
        WebSocket webSocket = AppConstants.httpClient.newWebSocket(req, messageConsumeCodeRunWebSocketListener);
        return new ListenedRunWebSocketSession(webSocket, messageConsumeCodeRunWebSocketListener);
    }

}
