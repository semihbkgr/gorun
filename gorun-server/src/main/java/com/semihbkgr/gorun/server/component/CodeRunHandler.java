package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.CodeRunContextt;

public interface CodeRunHandler {

    void registerRunning(Thread thread, CodeRunContextt codeRunContextt);

}
