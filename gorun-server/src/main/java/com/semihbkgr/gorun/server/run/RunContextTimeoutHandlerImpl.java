package com.semihbkgr.gorun.server.run;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class RunContextTimeoutHandlerImpl implements RunContextTimeoutHandler {

    private final Set<RunContext> runContextSet;
    private final long timeoutMs;

    public RunContextTimeoutHandlerImpl(@Value("${run}") Duration timeout) {
        this.timeoutMs = timeout.toMillis();
        this.runContextSet = new HashSet<>();
    }

    @Override
    public synchronized void addContext(@NonNull RunContext runContext) {
        runContextSet.add(runContext);
    }

    @Override
    public synchronized void removeContext(@NonNull RunContext runContext) {
        runContextSet.remove(runContext);
    }

    @Async
    @Scheduled(fixedDelayString = "${run.timeout-check}")
    @Override
    public void checkTimeout() {
        var currentTimeMS = System.currentTimeMillis();
        List<RunContext> runContextList = new ArrayList<>(runContextSet);
        runContextList.stream()
                .filter(runContext -> currentTimeMS - runContext.startTimeMS() > timeoutMs)
                .forEach(runContext -> {
                    runContext.setStatus(RunStatus.TIMEOUT);
                    runContext.process().destroyForcibly();
                    removeContext(runContext);
                    log.info("RunContext has been expired successfully");
                });
    }

}
