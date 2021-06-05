package com.semihbg.gorun.server.component;

import com.semihbg.gorun.server.socket.CodeRunContext;

public interface CodeRunHandler {

    void registerRunning(Thread thread, CodeRunContext codeRunContext);

}
