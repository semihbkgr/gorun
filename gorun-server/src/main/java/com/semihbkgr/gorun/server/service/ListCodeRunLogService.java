package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.run.DefaultRunContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListCodeRunLogService implements CodeRunLogService {

    private final List<DefaultRunContext> defaultRunContextList;
    private final int maxCount;

    public ListCodeRunLogService(@Value("${log-service.log-count:10}") int maxCount) {
        this.maxCount=maxCount;
        this.defaultRunContextList = new ArrayList<>(maxCount);
    }

    @Override
    public synchronized void log(DefaultRunContext defaultRunContext) {
        if(defaultRunContextList.size()>=maxCount)
            defaultRunContextList.remove(0);
        defaultRunContextList.add(defaultRunContext);
    }

    @Override
    public List<DefaultRunContext> getLogs() {
        return defaultRunContextList;
    }

}
