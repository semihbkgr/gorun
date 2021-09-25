package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.DefaultRunContextt;

public interface CodeRunHandler {

    void registerRunning(Thread thread, DefaultRunContextt defaultRunContextt);

}
