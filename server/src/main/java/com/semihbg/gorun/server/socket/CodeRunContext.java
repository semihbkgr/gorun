package com.semihbg.gorun.server.socket;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CodeRunContext {

    private String id;
    private long startTimestamp;
    private long endTimestamp;
    private final String code;
    private volatile State state;
    private Process process;

    public CodeRunContext(String code) {
        this.code = code;
        id= UUID.randomUUID().toString();
        state=State.READY;
        startTimestamp=-1;
        endTimestamp=-1;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeRunContext that = (CodeRunContext) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void start(){
        if(state==State.READY){
            startTimestamp=System.currentTimeMillis();
            state=State.RUNNING;
        }else throw new IllegalStateException();
    }

    public void end(){
        if(state==State.RUNNING){
            endTimestamp=System.currentTimeMillis();
            state=State.FINISHED;
        }else throw new IllegalStateException();
    }

    public void interrupt() {
        if(state==State.RUNNING){
            endTimestamp=System.currentTimeMillis();
            state=State.INTERRUPTED;
        }else throw new IllegalStateException();
    }

    public void unexpected(){
        state=State.UNEXPECTED;
    }

    public String getLogMessage(){
        return new StringBuilder().append("[")
        .append("StateTimestamp : ")
        .append(startTimestamp)
        .append(" , ")
        .append("RunTimeMs : ")
        .append(endTimestamp-startTimestamp)
        .append(" , ")
        .append("State : ")
        .append(state.name())
        .append("]")
        .toString();
    }

    public boolean isRunning(){
        return state==State.RUNNING;
    }

    public enum State{
        READY,
        RUNNING,
        FINISHED,
        INTERRUPTED,
        UNEXPECTED;
    }

}
