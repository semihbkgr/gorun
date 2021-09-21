package com.semihbg.gorun.run;

import com.semihbg.gorun.core.AppConstant;
import com.semihbg.gorun.core.AppContext;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DefaultCodeRunWebSocketClient implements CodeRunWebSocketClient {

    public static final CodeRunWebSocketClient instance;

    static {
        instance = new DefaultCodeRunWebSocketClient();
    }

    @Override
    public CodeRunWebSocketSession connect() {
        Request req = new Request.Builder().url(AppConstant.SERVER_CODE_RUN_URI).build();
        MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener = MessageConsumeCodeRunWebSocketListener.empty();
        WebSocket webSocket = AppContext.instance().httpClient.newWebSocket(req, messageConsumeCodeRunWebSocketListener);
        return new ListenedRunWebSocketSession(webSocket, messageConsumeCodeRunWebSocketListener);
    }

}
