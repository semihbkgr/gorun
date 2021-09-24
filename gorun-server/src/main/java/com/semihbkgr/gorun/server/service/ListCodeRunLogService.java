package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.run.CodeRunContextt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListCodeRunLogService implements CodeRunLogService {

    private final List<CodeRunContextt> codeRunContexttList;
    private final int maxCount;

    public ListCodeRunLogService(@Value("${log-service.log-count:10}") int maxCount) {
        this.maxCount=maxCount;
        this.codeRunContexttList = new ArrayList<>(maxCount);
    }

    @Override
    public synchronized void log(CodeRunContextt codeRunContextt) {
        if(codeRunContexttList.size()>=maxCount)
            codeRunContexttList.remove(0);
        codeRunContexttList.add(codeRunContextt);
    }

    @Override
    public List<CodeRunContextt> getLogs() {
        return codeRunContexttList;
    }

}
