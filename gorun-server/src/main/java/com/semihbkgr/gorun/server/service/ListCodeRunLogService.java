package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.run.RunContextImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListCodeRunLogService implements CodeRunLogService {

    private final List<RunContextImpl> runContextImplList;
    private final int maxCount;

    public ListCodeRunLogService(@Value("${log-service.log-count:10}") int maxCount) {
        this.maxCount=maxCount;
        this.runContextImplList = new ArrayList<>(maxCount);
    }

    @Override
    public synchronized void log(RunContextImpl runContextImpl) {
        if(runContextImplList.size()>=maxCount)
            runContextImplList.remove(0);
        runContextImplList.add(runContextImpl);
    }

    @Override
    public List<RunContextImpl> getLogs() {
        return runContextImplList;
    }

}
