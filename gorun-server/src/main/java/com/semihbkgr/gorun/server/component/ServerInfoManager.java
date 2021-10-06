package com.semihbkgr.gorun.server.component;

public interface ServerInfoManager {

    int getSessionCount();

    int increaseSessionCount();

    int decreaseSessionCount();

    int getExecutionCount();

    int increaseExecutionCount();

    int decreaseExecutionCount();

}
