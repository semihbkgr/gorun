package com.semihbkgr.gorun.server.run;

public abstract class AbstractRunContext implements RunContext {

    protected final Process process;
    private final String filename;
    protected RunStatus status;

    protected AbstractRunContext(Process process, String filename) {
        this.process = process;
        this.filename = filename;
        this.status = RunStatus.EXECUTING;
    }

    @Override
    public String filename() {
        return this.filename;
    }

    @Override
    public final RunStatus status() {
        return this.status;
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
