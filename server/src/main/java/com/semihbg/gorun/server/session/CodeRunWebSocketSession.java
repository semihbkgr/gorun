package com.semihbg.gorun.server.session;

import com.semihbg.gorun.server.service.CodeRunService;
import reactor.core.publisher.Flux;

public class CodeRunWebSocketSession {

    public final CodeRunService codeRunService;

    public CodeRunWebSocketSession(CodeRunService codeRunService) {
        this.codeRunService = codeRunService;
    }

    public Flux<String> executeCommand(String command) {
        String c=extractCommand(command);
        String cc=extractCode(command);
        System.out.println(c);
        System.out.println("---");
        System.out.println(cc);
        if(c==null)
           return Flux.empty();
        if(c.equalsIgnoreCase(CodeRunCommandConstants.RUN_CODE_COMMAND))
            if(cc!=null)
                return codeRunService.run(cc);
            else
                return Flux.empty();
        else if(c.equalsIgnoreCase(CodeRunCommandConstants.TERMINATE_CODE_COMMAND))
            return Flux.just("Terminate");
        else
            return Flux.just("Unknown command");
    }

    private String extractCommand(String command){
        if(command.contains(":")){
            int index=command.indexOf(":");
            return command.substring(0,index);
        }return null;
    }


    private String extractCode(String command){
        if(command.contains(":")){
            int index=command.indexOf(":");
            return command.substring(index+1);
        }return null;
    }

}
