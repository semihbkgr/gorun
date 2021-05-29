package com.semihbg.gorun.command;

@FunctionalInterface
public interface CodeCommandExecutor {

    void execute(CodeCommand command);

    default void run(String code){
        execute(new CodeCommand(CodeCommandType.RUN,code));
    }

    default void interrupt(){
        execute(new CodeCommand(CodeCommandType.INTERRUPT));
    }

}
