package com.semihbg.gorun.server.component;

import org.springframework.stereotype.Component;

import javax.annotation.processing.Processor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConcurrentProcessHandler implements ProcessHandler {

    private final ConcurrentSkipListSet<Process> processConcurrentSkipListSet;
    private final AtomicInteger waitCount;
    private final int waitCountThreshold;



    public ConcurrentProcessHandler() {
        this.processConcurrentSkipListSet=new ConcurrentSkipListSet<>();
        processConcurrentSkipListSet.
        this.waitCount=new AtomicInteger(0);
        waitCountThreshold=
    }

    @Override
    public boolean tryAcquire() {
        if(waitCount)
    }

    @Override
    public Process getProcess() {
        return null;
    }
}
