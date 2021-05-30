package com.semihbg.gorun;

import com.semihbg.gorun.socket.CodeRunWebSocketContext;
import com.semihbg.gorun.socket.DefaultCodeRunWebSocketClient;

import java.util.Optional;

public class AppSocketSessionHolder {

    private volatile static Optional<CodeRunWebSocketContext> sessionInstanceOptional;

    static{
        sessionInstanceOptional=Optional.empty();
    }

    public static void connect(){
        sessionInstanceOptional=Optional.of(DefaultCodeRunWebSocketClient.instance.connect());
    }

    public static boolean isConnected(){
        return sessionInstanceOptional.isPresent();
    }

    public static CodeRunWebSocketContext getSession(){
        return sessionInstanceOptional.get();
    }

}
