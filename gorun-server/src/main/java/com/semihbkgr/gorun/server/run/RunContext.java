package com.semihbkgr.gorun.server.run;

public interface RunContext {

    RunState getState();

    RunInfo getRunInfo();

    void start();

    void interrupt();

    void send(String data);

    void terminate();

}
