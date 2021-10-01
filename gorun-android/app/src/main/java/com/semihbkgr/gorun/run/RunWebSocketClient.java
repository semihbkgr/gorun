package com.semihbkgr.gorun.run;

import androidx.annotation.Nullable;
import okhttp3.WebSocketListener;

public interface RunWebSocketClient {

    void connect(@Nullable WebSocketListener webSocketListener);

    boolean hasSession();

    RunWebSocketSession session();

}
