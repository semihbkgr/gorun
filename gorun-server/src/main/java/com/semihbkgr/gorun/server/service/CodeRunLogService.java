package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.run.CodeRunContextt;

import java.util.List;

public interface CodeRunLogService {

    void log(CodeRunContextt codeRunContextt);

    List<CodeRunContextt> getLogs();

}
