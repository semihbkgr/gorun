package com.semihbg.gorun.command;

public class CodeCommand {

    public final Command command;
    public final String code;

    public CodeCommand(Command command, String code) {
        this.command = command;
        this.code = code;
    }

    public CodeCommand(Command command) {
        this.command = command;
        code=null;
    }

    public Command getCommandType() {
        return command;
    }

    public String getCode() {
        return code;
    }

    public String getStringCommand(){

    }

}
