package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.run.DefaultRunContextt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CodeRunHandlerImpl implements CodeRunHandler {

//    private final Thread thread;
//    private final ConcurrentHashMap<DefaultRunContextt,Thread> codeRunContextThreadConcurrentHashMap;
//    private final long expireTimeIntervalMs;

    public CodeRunHandlerImpl(@Value("${code-run-service.max-time-second:25}") int maxTimeSecond) {
//        this.codeRunContextThreadConcurrentHashMap=new ConcurrentHashMap<>();
//        this.expireTimeIntervalMs =maxTimeSecond*1000L;
//        this.thread=new Thread(()->{
//            while(true){
//                long currentTimeMs=System.currentTimeMillis();
//                for(DefaultRunContextt defaultRunContextt :codeRunContextThreadConcurrentHashMap.keySet()){
//                    if(defaultRunContextt.isRunning()){
//                        if(currentTimeMs- defaultRunContextt.getStartTimestamp()>expireTimeIntervalMs){
//                            defaultRunContextt.interrupt();
//                            codeRunContextThreadConcurrentHashMap.get(defaultRunContextt).interrupt();
//                            log.info("DefaultRunContextt has been interrupted, It exceed expire time interval, ExpireTimeIntervalMs: {}",expireTimeIntervalMs);
//                        }
//                    }else{
//                        codeRunContextThreadConcurrentHashMap.remove(defaultRunContextt);
//                    }
//                }
//            }
//        });
//        thread.setName(this.getClass().getName().concat("Thread"));
//        thread.setDaemon(true);
//        thread.start();
    }

    @Override
    public void registerRunning(Thread thread, DefaultRunContextt defaultRunContextt) {
       //codeRunContextThreadConcurrentHashMap.put(defaultRunContextt,thread);
    }

    public void registerRunning(DefaultRunContextt defaultRunContextt){
        this.registerRunning(Thread.currentThread(), defaultRunContextt);
    }


}
