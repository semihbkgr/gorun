package com.semihbkgr.gorun.server.metric;

public interface ServerInfoManager {

    int getSessionCount();

    int increaseSessionCount();

    int decreaseSessionCount();

    int getCurrentExecutionCount();

    int increaseCurrentExecutionCount();

    int decreaseCurrentExecutionCount();

    int getExecutionCount();

    int increaseExecutionCount();

}
