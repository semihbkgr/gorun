package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.RunContext;
import com.semihbkgr.gorun.server.run.RunStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class RunContextTimeoutHandlerImpl implements RunContextTimeoutHandler {

    private final long timeoutMS;
    private final Set<RunContext> runContextSet;

    public RunContextTimeoutHandlerImpl(@Value("${run.timeout-ms:30_000}") long timeoutMS) {
        this.timeoutMS = timeoutMS;
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
    @Scheduled(fixedDelayString = "${run.timeout-check-time-interval-ms:500}")
    @Override
    public void checkTimeout() {
        long currentTimeMS = System.currentTimeMillis();
        List<RunContext> runContextList = new ArrayList<>(runContextSet);
        runContextList.stream()
                .filter(runContext -> currentTimeMS - runContext.startTimeMS() > timeoutMS)
                .forEach(runContext -> {
                    runContext.setStatus(RunStatus.TIMEOUT);
                    runContext.process().destroyForcibly();
                });
    }

}
