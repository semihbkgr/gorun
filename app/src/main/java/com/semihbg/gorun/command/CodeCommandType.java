package com.semihbg.gorun.command;

public enum CodeCommandType {

    RUN("run"),
    INTERRUPT("interrupt");

    public final String prefix;

    CodeCommandType(String prefix) {
        this.prefix = prefix;
    }

    public String getFullPrefix(){
        return this.prefix.concat(":");
    }

}
