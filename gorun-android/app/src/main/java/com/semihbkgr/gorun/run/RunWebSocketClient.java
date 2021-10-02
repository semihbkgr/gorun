package com.semihbkgr.gorun.run;

import androidx.annotation.Nullable;
import okhttp3.WebSocketListener;

public interface RunWebSocketClient {

    RunWebSocketSession connect(@Nullable WebSocketListener webSocketListener);

}
