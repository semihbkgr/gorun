package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.RunContextImpl;

public interface CodeRunHandler {

    void registerRunning(Thread thread, RunContextImpl runContextImpl);

}
