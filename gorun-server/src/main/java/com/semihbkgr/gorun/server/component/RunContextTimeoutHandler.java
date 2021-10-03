package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.RunContext;

public interface RunContextTimeoutHandler {

    void addContext(RunContext runContext);

    void removeContext(RunContext runContext);

    void checkTimeout();

}
