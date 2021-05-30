package com.semihbg.gorun.server.socket;

import lombok.Getter;

@Getter
public class CodeRunContext {

    private long startTimestamp;
    private long endTimestamp;
    private final String code;
    private volatile State state;

    public CodeRunContext(String code) {
        this.code = code;
        state=State.READY;
        startTimestamp=-1;
        endTimestamp=-1;
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
