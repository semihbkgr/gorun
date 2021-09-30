package com.semihbkgr.gorun.run;

import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DefaultCodeRunWebSocketClient implements CodeRunWebSocketClient {

    public static final CodeRunWebSocketClient instance;

    static {
        instance = new DefaultCodeRunWebSocketClient();
    }

    @Override
    public CodeRunWebSocketSession connect() {
//        Request req = new Request.Builder().url(AppConstants.Nets.SERVER_CODE_RUN_URI).build();
//        MessageConsumeCodeRunWebSocketListener messageConsumeCodeRunWebSocketListener = MessageConsumeCodeRunWebSocketListener.empty();
//        WebSocket webSocket = AppContext.instance().httpClient.newWebSocket(req, messageConsumeCodeRunWebSocketListener);
//        return new ListenedRunWebSocketSession(webSocket, messageConsumeCodeRunWebSocketListener);
        return null;
    }

}
