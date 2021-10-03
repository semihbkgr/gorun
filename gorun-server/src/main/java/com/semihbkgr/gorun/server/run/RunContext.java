package com.semihbkgr.gorun.server.run;

public interface RunContext {

    RunStatus status();

    String filename();

    long startTimeMS();

    void setStatus(RunStatus status);

    Process process();

}
