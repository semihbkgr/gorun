package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.socket.CodeRunContext;

import java.util.List;

public interface CodeRunLogService {

    void log(CodeRunContext codeRunContext);

    List<CodeRunContext> getLogs();

}
