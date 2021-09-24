package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.run.DefaultRunContext;

import java.util.List;

public interface CodeRunLogService {

    void log(DefaultRunContext defaultRunContext);

    List<DefaultRunContext> getLogs();

}
