package com.semihbg.gorun.socket;

public interface CodeRunWebSocketClient {

    public String CODE_RUN_SERVER_URI="ws://192.168.1.7:8080/run";

    CodeRunWebSocketSession connect();

}
