package com.semihbkgr.gorun.server.run;

public interface RunContextTimeoutHandler {

    void addContext(RunContext runContext);

    void removeContext(RunContext runContext);

    void checkTimeout();

}
