package com.semihbg.gorun.run;

import com.semihbg.gorun.message.Command;
import com.semihbg.gorun.message.Message;

import java.util.concurrent.atomic.AtomicBoolean;

public class CodeRunContext {

    public static final CodeRunContext instance;

    static{
        instance=new CodeRunContext( DefaultCodeRunWebSocketClient.instance.connect());
    }

    private CodeRunWebSocketSession codeRunWebSocketSession;
    private AtomicBoolean isRunning;

    public CodeRunContext(CodeRunWebSocketSession codeRunWebSocketSession) {
        this.codeRunWebSocketSession = codeRunWebSocketSession;
        this.isRunning=new AtomicBoolean(false);
    }

    public void run(String code){
        if(!isRunning.get()){
            codeRunWebSocketSession.sendMessage(Message.of(Command.RUN,code));
        }else throw new IllegalArgumentException("Context already has an on going code running");
    }

    public void interrupt(){
        if(isRunning.get()){
            codeRunWebSocketSession.sendMessage(Message.of(Command.INTERRUPT));
        }else throw new IllegalArgumentException("Context already have not an on going code running");
    }

    public void disconnect(){
        codeRunWebSocketSession.sendMessage(Message.of(Command.DISCONNECT));
    }

    public CodeRunWebSocketSession getCodeRunWebSocketSession() {
        return codeRunWebSocketSession;
    }

}
