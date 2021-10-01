package com.semihbkgr.gorun.run;

import com.semihbkgr.gorun.message.Message;

import java.util.function.Consumer;

public interface RunWebSocketSession {

    void sendMessage(Message message);

    void addMessageConsumer(Consumer<Message> consumer);

    void close();

    boolean connected();

}
