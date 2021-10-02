package com.semihbkgr.gorun.run;

import com.semihbkgr.gorun.message.Message;

import java.util.function.Consumer;

public interface RunWebSocketSession {

    boolean sendMessage(Message message);

    boolean addMessageConsumer(Consumer<Message> consumer);

    boolean close();

}
