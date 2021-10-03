package com.semihbkgr.gorun.server.component;

import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class ProcessTimeoutHandlerImpl implements ProcessTimeoutHandler {

    private final long timeoutMS;
    private final HashMap<Process,Long> processStartTimeMap;

    public ProcessTimeoutHandlerImpl(@Value("${run.timeout-ms:30_000}") long timeoutMS) {
        this.timeoutMS = timeoutMS;
        this.processStartTimeMap=new HashMap<>();
    }

    @Override
    public void addProcess(@NonNull Process process, long startTimeMS) {
        processStartTimeMap.put(process,startTimeMS);
    }

    @Override
    public void removeProcess(@NonNull Process process) {
        processStartTimeMap.remove(process);
    }

    @Async
    @Scheduled(fixedDelayString = "${run.timeout-check-time-interval-ms:500}")
    @Override
    public void checkTimeout() {
        long currentTimeMS=System.currentTimeMillis();
        Set<Map.Entry<Process,Long>> entryList=new HashSet<>(processStartTimeMap.entrySet());
        entryList.forEach(e->{
            if(currentTimeMS-e.getValue()>timeoutMS)
                e.getKey().destroyForcibly();
        });
    }

}
