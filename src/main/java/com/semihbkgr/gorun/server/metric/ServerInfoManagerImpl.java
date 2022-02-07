package com.semihbkgr.gorun.server.metric;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ServerInfoManagerImpl implements ServerInfoManager {

    private final AtomicInteger sessionCount;
    private final AtomicInteger executionCount;
    private final AtomicInteger currentExecutionCount;

    public ServerInfoManagerImpl() {
        this.sessionCount = new AtomicInteger(0);
        this.executionCount = new AtomicInteger(0);
        this.currentExecutionCount = new AtomicInteger(0);
    }

    @Override
    public int getSessionCount() {
        return sessionCount.get();
    }

    @Override
    public int increaseSessionCount() {
        return sessionCount.incrementAndGet();
    }

    @Override
    public int decreaseSessionCount() {
        return sessionCount.decrementAndGet();
    }

    @Override
    public int getCurrentExecutionCount() {
        return currentExecutionCount.get();
    }

    @Override
    public int increaseCurrentExecutionCount() {
        return currentExecutionCount.incrementAndGet();
    }

    @Override
    public int decreaseCurrentExecutionCount() {
        return currentExecutionCount.decrementAndGet();
    }

    @Override
    public int getExecutionCount() {
        return executionCount.get();
    }

    @Override
    public int increaseExecutionCount() {
        return executionCount.incrementAndGet();
    }

}
