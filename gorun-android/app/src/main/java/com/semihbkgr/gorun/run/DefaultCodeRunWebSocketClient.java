package com.semihbkgr.gorun.run;

public class DefaultCodeRunWebSocketClient implements RunWebSocketClient {

    @Override
    public CodeRunWebSocketSession connect() {
//        Request req = new Request.Builder().url(AppConstants.Nets.SERVER_CODE_RUN_URI).build();
//        MessageWebSocketListener messageConsumeCodeRunWebSocketListener = MessageWebSocketListener.empty();
//        WebSocket webSocket = AppContext.instance().httpClient.newWebSocket(req, messageConsumeCodeRunWebSocketListener);
//        return new ListenedRunWebSocketSession(webSocket, messageConsumeCodeRunWebSocketListener);
        return null;
    }

}
