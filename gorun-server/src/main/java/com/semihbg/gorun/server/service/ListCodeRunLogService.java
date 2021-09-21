package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.socket.CodeRunContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListCodeRunLogService implements CodeRunLogService {

    private final List<CodeRunContext> codeRunContextList;
    private final int maxCount;

    public ListCodeRunLogService(@Value("${log-service.log-count:10}") int maxCount) {
        this.maxCount=maxCount;
        this.codeRunContextList = new ArrayList<>(maxCount);
    }

    @Override
    public synchronized void log(CodeRunContext codeRunContext) {
        if(codeRunContextList.size()>=maxCount)
            codeRunContextList.remove(0);
        codeRunContextList.add(codeRunContext);
    }

    @Override
    public List<CodeRunContext> getLogs() {
        return codeRunContextList;
    }

}
