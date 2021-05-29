package com.semihbg.gorun.server.component;

public interface ProcessHandler {

    boolean tryAcquire ();

    Process getProcess();

}
