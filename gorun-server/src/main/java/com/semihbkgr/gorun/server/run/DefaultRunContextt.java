package com.semihbkgr.gorun.server.run;

import lombok.Getter;

import java.io.PrintWriter;
import java.util.UUID;

@Getter
public class DefaultRunContextt {

    private final String code;
    private volatile State state;
    private Process process;
    private PrintWriter printWriter;

    public DefaultRunContextt(String code) {
        this.code = code;
        state=State.READY;
    }

    public void start(Process process){
        if(state==State.READY){
            this.process=process;
            this.printWriter=new PrintWriter(process.getOutputStream(),true);
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
