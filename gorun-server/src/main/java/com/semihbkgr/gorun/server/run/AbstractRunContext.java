package com.semihbkgr.gorun.server.run;

public abstract class AbstractRunContext implements RunContext{

    protected final Process process;
    protected RunStatus status;

    protected AbstractRunContext(Process process) {
        this.process=process;
        this.status=RunStatus.EXECUTING;
    }

    @Override
    public final RunStatus status() {
        return this.status;
    }


    @Override
    public final Process process(){
        return this.process;
    }


}
