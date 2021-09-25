package com.semihbkgr.gorun.server.run;

public abstract class AbstractRunContext implements RunContext{

    protected RunStatus runStatus;
    protected final RunInfo runInfo;

    protected AbstractRunContext(String code) {
        this.runInfo = new RunInfo(code);
    }

    @Override
    public final RunStatus getStatus() {
        return this.getStatus();
    }

    @Override
    public final RunInfo getRunInfo() {
        return this.runInfo;
    }

}
