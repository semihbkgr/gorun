package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.RunContext;

public interface ProcessTimeoutHandler {

    void addProcess(Process process, long startTimeMS);

    void removeProcess(Process process);

    void checkTimeout();

}
