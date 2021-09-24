package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.RunContextImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CodeRunHandlerImpl implements CodeRunHandler {

    private final Thread thread;
    private final ConcurrentHashMap<RunContextImpl,Thread> codeRunContextThreadConcurrentHashMap;
    private final long expireTimeIntervalMs;

    public CodeRunHandlerImpl(@Value("${code-run-service.max-time-second:25}") int maxTimeSecond) {
        this.codeRunContextThreadConcurrentHashMap=new ConcurrentHashMap<>();
        this.expireTimeIntervalMs =maxTimeSecond*1000L;
        this.thread=new Thread(()->{
            while(true){
                long currentTimeMs=System.currentTimeMillis();
                for(RunContextImpl runContextImpl :codeRunContextThreadConcurrentHashMap.keySet()){
                    if(runContextImpl.isRunning()){
                        if(currentTimeMs- runContextImpl.getStartTimestamp()>expireTimeIntervalMs){
                            runContextImpl.interrupt();
                            codeRunContextThreadConcurrentHashMap.get(runContextImpl).interrupt();
                            log.info("RunContextImpl has been interrupted, It exceed expire time interval, ExpireTimeIntervalMs: {}",expireTimeIntervalMs);
                        }
                    }else{
                        codeRunContextThreadConcurrentHashMap.remove(runContextImpl);
                    }
                }
            }
        });
        thread.setName(this.getClass().getName().concat("Thread"));
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void registerRunning(Thread thread, RunContextImpl runContextImpl) {
        codeRunContextThreadConcurrentHashMap.put(runContextImpl,thread);
    }

    public void registerRunning(RunContextImpl runContextImpl){
        this.registerRunning(Thread.currentThread(), runContextImpl);
    }


}
