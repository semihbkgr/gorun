package com.semihbkgr.gorun.run;

import com.semihbkgr.gorun.message.Message;

public interface WebSocketSession {

    void sendMessage(Message message);

}
