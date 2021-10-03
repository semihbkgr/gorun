package com.semihbkgr.gorun.server.component;

public interface ProcessTimeoutHandler {

    void addProcess(Process process, long startTimeMS);

    void removeProcess(Process process);

    void checkTimeout();

}
