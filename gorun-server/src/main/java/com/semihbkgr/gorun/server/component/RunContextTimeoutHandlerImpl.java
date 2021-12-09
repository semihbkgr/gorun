package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.RunContext;
import com.semihbkgr.gorun.server.run.RunProperties;
import com.semihbkgr.gorun.server.run.RunStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class RunContextTimeoutHandlerImpl implements RunContextTimeoutHandler {

    private final RunProperties runProperties;
    private final Set<RunContext> runContextSet;

    public RunContextTimeoutHandlerImpl(RunProperties runProperties) {
        this.runProperties = runProperties;
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
                .filter(runContext -> currentTimeMS - runContext.startTimeMS() > runProperties.getTimeout().toMillis())
                .forEach(runContext -> {
                    runContext.setStatus(RunStatus.TIMEOUT);
                    runContext.process().destroyForcibly();
                });
    }

}
