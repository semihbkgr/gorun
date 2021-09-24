package com.semihbkgr.gorun.server.run;

public abstract class AbstractRunContext implements RunContext{

    protected RunState runState;
    protected final RunInfo runInfo;

    protected AbstractRunContext(RunInfo runInfo) {
        this.runInfo = runInfo;
    }

}
