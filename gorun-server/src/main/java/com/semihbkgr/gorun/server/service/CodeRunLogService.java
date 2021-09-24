package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.run.RunContextImpl;

import java.util.List;

public interface CodeRunLogService {

    void log(RunContextImpl runContextImpl);

    List<RunContextImpl> getLogs();

}
