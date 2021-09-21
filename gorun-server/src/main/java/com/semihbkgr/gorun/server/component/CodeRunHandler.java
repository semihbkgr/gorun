package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.socket.CodeRunContext;

public interface CodeRunHandler {

    void registerRunning(Thread thread, CodeRunContext codeRunContext);

}
