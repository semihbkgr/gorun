package com.semihbkgr.gorun.run;

import com.semihbkgr.gorun.message.Command;
import com.semihbkgr.gorun.message.Message;

public class CodeRunContext {

    public static final CodeRunContext instance;

    static{
        instance=new CodeRunContext( DefaultCodeRunWebSocketClient.instance.connect());
    }

    private CodeRunWebSocketSession codeRunWebSocketSession;


    public CodeRunContext(CodeRunWebSocketSession codeRunWebSocketSession) {
        this.codeRunWebSocketSession = codeRunWebSocketSession;
    }

    public void run(String code){
        codeRunWebSocketSession.sendMessage(Message.of(Command.RUN,code));
    }

    public void send(String command){
        codeRunWebSocketSession.sendMessage(Message.of(Command.INPUT,command));
    }

    public void interrupt(){
        codeRunWebSocketSession.sendMessage(Message.of(Command.INTERRUPT));
    }

    public void disconnect(){
        codeRunWebSocketSession.sendMessage(Message.of(Command.DISCONNECT));
    }

    public CodeRunWebSocketSession getCodeRunWebSocketSession() {
        return codeRunWebSocketSession;
    }

}
