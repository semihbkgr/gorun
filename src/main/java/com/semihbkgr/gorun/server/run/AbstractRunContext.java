package com.semihbkgr.gorun.server.run;

public abstract class AbstractRunContext implements RunContext {

    protected final Process process;
    protected final String filename;
    protected final long startTimeMS;
    protected RunStatus status;

    protected AbstractRunContext(Process process, String filename, long startTimeMS) {
        this.process = process;
        this.filename = filename;
        this.startTimeMS = startTimeMS;
        this.status = RunStatus.EXECUTING;
    }

    @Override
    public final RunStatus status() {
        return this.status;
    }

    @Override
    public String filename() {
        return this.filename;
    }

    @Override
    public long startTimeMS() {
        return this.startTimeMS;
    }

    @Override
    public final void setStatus(RunStatus status) {
        this.status = status;
    }

    @Override
    public final Process process() {
        return this.process;
    }


}
