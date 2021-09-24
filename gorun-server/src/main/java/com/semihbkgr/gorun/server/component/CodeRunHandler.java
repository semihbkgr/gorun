package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.DefaultRunContext;

public interface CodeRunHandler {

    void registerRunning(Thread thread, DefaultRunContext defaultRunContext);

}
