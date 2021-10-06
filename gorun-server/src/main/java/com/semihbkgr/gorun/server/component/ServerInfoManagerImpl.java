package com.semihbkgr.gorun.server.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ServerInfoManagerImpl implements ServerInfoManager {

    private final AtomicInteger sessionCount;
    private final AtomicInteger executionCount;

    public ServerInfoManagerImpl() {
        this.sessionCount = new AtomicInteger(0);
        this.executionCount = new AtomicInteger(0);
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
    public int getExecutionCount() {
        return executionCount.get();
    }

    @Override
    public int increaseExecutionCount() {
        return executionCount.incrementAndGet();
    }

    @Override
    public int decreaseExecutionCount() {
        return executionCount.decrementAndGet();
    }

}
