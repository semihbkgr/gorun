package com.semihbkgr.gorun.server.run;

import com.semihbkgr.gorun.server.run.RunContext;

public interface RunContextTimeoutHandler {

    void addContext(RunContext runContext);

    void removeContext(RunContext runContext);

    void checkTimeout();

}
