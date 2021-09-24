package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.CodeRunContextt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CodeRunHandlerImpl implements CodeRunHandler {

    private final Thread thread;
    private final ConcurrentHashMap<CodeRunContextt,Thread> codeRunContextThreadConcurrentHashMap;
    private final long expireTimeIntervalMs;

    public CodeRunHandlerImpl(@Value("${code-run-service.max-time-second:25}") int maxTimeSecond) {
        this.codeRunContextThreadConcurrentHashMap=new ConcurrentHashMap<>();
        this.expireTimeIntervalMs =maxTimeSecond*1000L;
        this.thread=new Thread(()->{
            while(true){
                long currentTimeMs=System.currentTimeMillis();
                for(CodeRunContextt codeRunContextt :codeRunContextThreadConcurrentHashMap.keySet()){
                    if(codeRunContextt.isRunning()){
                        if(currentTimeMs- codeRunContextt.getStartTimestamp()>expireTimeIntervalMs){
                            codeRunContextt.interrupt();
                            codeRunContextThreadConcurrentHashMap.get(codeRunContextt).interrupt();
                            log.info("CodeRunContextt has been interrupted, It exceed expire time interval, ExpireTimeIntervalMs: {}",expireTimeIntervalMs);
                        }
                    }else{
                        codeRunContextThreadConcurrentHashMap.remove(codeRunContextt);
                    }
                }
            }
        });
        thread.setName(this.getClass().getName().concat("Thread"));
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void registerRunning(Thread thread, CodeRunContextt codeRunContextt) {
        codeRunContextThreadConcurrentHashMap.put(codeRunContextt,thread);
    }

    public void registerRunning(CodeRunContextt codeRunContextt){
        this.registerRunning(Thread.currentThread(), codeRunContextt);
    }


}
