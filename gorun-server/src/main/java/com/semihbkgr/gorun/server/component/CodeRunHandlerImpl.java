package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.socket.CodeRunContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CodeRunHandlerImpl implements CodeRunHandler {

    private final Thread thread;
    private final ConcurrentHashMap<CodeRunContext,Thread> codeRunContextThreadConcurrentHashMap;
    private final long expireTimeIntervalMs;

    public CodeRunHandlerImpl(@Value("${code-run-service.max-time-second:25}") int maxTimeSecond) {
        this.codeRunContextThreadConcurrentHashMap=new ConcurrentHashMap<>();
        this.expireTimeIntervalMs =maxTimeSecond*1000L;
        this.thread=new Thread(()->{
            while(true){
                long currentTimeMs=System.currentTimeMillis();
                for(CodeRunContext codeRunContext:codeRunContextThreadConcurrentHashMap.keySet()){
                    if(codeRunContext.isRunning()){
                        if(currentTimeMs-codeRunContext.getStartTimestamp()>expireTimeIntervalMs){
                            codeRunContext.interrupt();
                            codeRunContextThreadConcurrentHashMap.get(codeRunContext).interrupt();
                            log.info("CodeRunContext has been interrupted, It exceed expire time interval, ExpireTimeIntervalMs: {}",expireTimeIntervalMs);
                        }
                    }else{
                        codeRunContextThreadConcurrentHashMap.remove(codeRunContext);
                    }
                }
            }
        });
        thread.setName(this.getClass().getName().concat("Thread"));
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void registerRunning(Thread thread, CodeRunContext codeRunContext) {
        codeRunContextThreadConcurrentHashMap.put(codeRunContext,thread);
    }

    public void registerRunning(CodeRunContext codeRunContext){
        this.registerRunning(Thread.currentThread(),codeRunContext);
    }


}
