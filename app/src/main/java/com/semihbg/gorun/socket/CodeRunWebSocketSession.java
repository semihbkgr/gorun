package com.semihbg.gorun.socket;

import com.semihbg.gorun.command.CodeCommand;
import com.semihbg.gorun.command.CodeCommandExecutor;
import okhttp3.WebSocket;

import java.util.function.Consumer;

public abstract class CodeRunWebSocketSession implements WebSocketSession, CodeCommandExecutor {

    protected final WebSocket webSocket;

    protected CodeRunWebSocketSession(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public void send(String data){
        webSocket.send(data);
    }

    @Override
    public void close() {
        webSocket.cancel();
    }

    @Override
    public void execute(CodeCommand command) {
        this.send(command.getCommandAsStringData());
    }

    public abstract void addOutputConsumer(Consumer<String> messageConsumer);

}
