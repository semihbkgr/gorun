package com.semihbkgr.gorun.server.run;

import lombok.Getter;

import java.io.PrintWriter;
import java.util.UUID;

@Getter
public class RunContextImpl {

    private String id;
    private long startTimestamp;
    private long endTimestamp;
    private final String code;
    private volatile State state;
    private Process process;
    private PrintWriter printWriter;

    public RunContextImpl(String code) {
        this.code = code;
        id= UUID.randomUUID().toString();
        state=State.READY;
        startTimestamp=-1;
        endTimestamp=-1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunContextImpl that = (RunContextImpl) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void start(Process process){
        if(state==State.READY){
            this.process=process;
            this.printWriter=new PrintWriter(process.getOutputStream(),true);
            startTimestamp=System.currentTimeMillis();
            state=State.RUNNING;
        }else throw new IllegalStateException();
    }

    public void end(){
        if(state==State.RUNNING){
            terminate();
            state=State.FINISHED;
        }else throw new IllegalStateException();
    }

    public void interrupt() {
        if(state==State.RUNNING){
            terminate();
            state=State.INTERRUPTED;
        }else throw new IllegalStateException();
    }

    public void unexpected(){
        terminate();
        state=State.UNEXPECTED;
    }

    private void terminate(){
        process=null;
        printWriter.close();
        printWriter=null;
        endTimestamp=System.currentTimeMillis();
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

    public void sendInput(String str){
        printWriter.println(str);
    }

    public enum State{
        READY,
        RUNNING,
        FINISHED,
        INTERRUPTED,
        UNEXPECTED;
    }

}
