package com.semihbg.gorun.server.util;

import com.semihbg.gorun.server.message.Command;
import com.semihbg.gorun.server.message.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InputBuilder {

    private final Map<Command,Consumer<? super String>> consumerMap;
    private final Map<Command,TimeBodyPair> commandTimeBodyPairMap;
    private final int delayMs;

    public InputBuilder(int delayMs) {
        this.delayMs=delayMs;
        this.consumerMap = new ConcurrentHashMap<>();
        this.commandTimeBodyPairMap=new ConcurrentHashMap<>();
        Thread thread=new Thread(()-> {
            while(true){
                for(var entry:commandTimeBodyPairMap.entrySet()){
                    if(entry.getValue().lastUpdateTimeMs>0 && System.currentTimeMillis()-entry.getValue().lastUpdateTimeMs>=delayMs && consumerMap.containsKey(entry.getKey())){
                        consumerMap.get(entry.getKey()).accept(entry.getValue().body);
                        entry.getValue().lastUpdateTimeMs=-1;
                        entry.getValue().body="";
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("InputBuilderThread");
        thread.start();
    }

    public void addConsumer(Command command,Consumer<? super String> stringConsumer){
        consumerMap.put(command,stringConsumer);
    }

    public void message(Message message){
        TimeBodyPair timeBodyPair=commandTimeBodyPairMap.getOrDefault(message.command,null);
        if(timeBodyPair!=null)
            timeBodyPair.addBody(message.body);
        else{
            TimeBodyPair newTimeBodyPair=new TimeBodyPair();
            newTimeBodyPair.addBody(message.body);
            commandTimeBodyPairMap.put(message.command,newTimeBodyPair);
        }
    }

    private static class TimeBodyPair{

        public long lastUpdateTimeMs;
        public String body;

        public TimeBodyPair() {
            this.lastUpdateTimeMs = -1;
            this.body="";
        }

        public void addBody(String str){
            if(str!=null){
                lastUpdateTimeMs =System.currentTimeMillis();
                body=body.concat(str);
            }
        }

    }

}
