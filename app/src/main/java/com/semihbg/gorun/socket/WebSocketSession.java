package com.semihbg.gorun.socket;

import com.semihbg.gorun.message.Message;

public interface WebSocketSession {

    void sendMessage(Message message);

}
