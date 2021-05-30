package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.socket.CodeRunContext;

public interface CodeRunLogService {

    void log(CodeRunContext codeRunContext);

    String getLogAsString();

}
