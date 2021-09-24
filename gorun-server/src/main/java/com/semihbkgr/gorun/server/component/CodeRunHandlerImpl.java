package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.DefaultRunContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CodeRunHandlerImpl implements CodeRunHandler {

    private final Thread thread;
    private final ConcurrentHashMap<DefaultRunContext,Thread> codeRunContextThreadConcurrentHashMap;
    private final long expireTimeIntervalMs;

    public CodeRunHandlerImpl(@Value("${code-run-service.max-time-second:25}") int maxTimeSecond) {
        this.codeRunContextThreadConcurrentHashMap=new ConcurrentHashMap<>();
        this.expireTimeIntervalMs =maxTimeSecond*1000L;
        this.thread=new Thread(()->{
            while(true){
                long currentTimeMs=System.currentTimeMillis();
                for(DefaultRunContext defaultRunContext :codeRunContextThreadConcurrentHashMap.keySet()){
                    if(defaultRunContext.isRunning()){
                        if(currentTimeMs- defaultRunContext.getStartTimestamp()>expireTimeIntervalMs){
                            defaultRunContext.interrupt();
                            codeRunContextThreadConcurrentHashMap.get(defaultRunContext).interrupt();
                            log.info("DefaultRunContext has been interrupted, It exceed expire time interval, ExpireTimeIntervalMs: {}",expireTimeIntervalMs);
                        }
                    }else{
                        codeRunContextThreadConcurrentHashMap.remove(defaultRunContext);
                    }
                }
            }
        });
        thread.setName(this.getClass().getName().concat("Thread"));
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void registerRunning(Thread thread, DefaultRunContext defaultRunContext) {
        codeRunContextThreadConcurrentHashMap.put(defaultRunContext,thread);
    }

    public void registerRunning(DefaultRunContext defaultRunContext){
        this.registerRunning(Thread.currentThread(), defaultRunContext);
    }


}
