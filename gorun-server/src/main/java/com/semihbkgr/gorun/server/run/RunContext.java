package com.semihbkgr.gorun.server.run;

public interface RunContext {

    RunStatus getStatus();

    RunInfo getRunInfo();

    void start();

    void interrupt();

    void send(String data);

    void terminate();

}
