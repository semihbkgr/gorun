package com.semihbg.gorun.socket;

public interface WebSocketSession {

    void send(String data);

    void close();

}
