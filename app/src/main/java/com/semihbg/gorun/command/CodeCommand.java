package com.semihbg.gorun.command;

public class CodeCommand {

    public final CodeCommandType codeCommandType;
    public final String code;

    public CodeCommand(CodeCommandType codeCommandType, String code) {
        this.codeCommandType = codeCommandType;
        this.code = code;
    }

    public CodeCommand(CodeCommandType codeCommandType) {
        this.codeCommandType = codeCommandType;
        code=null;
    }

    public CodeCommandType getCommandType() {
        return codeCommandType;
    }

    public String getCode() {
        return code;
    }

    public String getCommandAsStringData(){
        return codeCommandType.getFullPrefix().concat(code);
    }

}

