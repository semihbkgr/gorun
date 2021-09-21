package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.socket.CodeRunContext;

import java.util.List;

public interface CodeRunLogService {

    void log(CodeRunContext codeRunContext);

    List<CodeRunContext> getLogs();

}
