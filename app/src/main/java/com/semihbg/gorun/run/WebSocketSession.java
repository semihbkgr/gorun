package com.semihbg.gorun.run;

import com.semihbg.gorun.message.Message;

public interface WebSocketSession {

    void sendMessage(Message message);

}
