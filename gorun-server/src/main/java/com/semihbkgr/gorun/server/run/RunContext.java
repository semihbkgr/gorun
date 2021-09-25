package com.semihbkgr.gorun.server.run;

public interface RunContext {

    RunStatus status();

    void setStatus(RunStatus status);

    Process process();

}
