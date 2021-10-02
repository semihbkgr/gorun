package com.semihbkgr.gorun.server.run;

public interface RunContext {

    RunStatus status();

    String filename();

    void setStatus(RunStatus status);

    Process process();

}
