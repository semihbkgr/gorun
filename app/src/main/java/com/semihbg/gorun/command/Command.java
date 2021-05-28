package com.semihbg.gorun.command;

public enum Command {

    RUN("run"),
    INTERRUPT("interrupt");

    public final String prefix;

    Command(String prefix) {
        this.prefix = prefix;
    }

    public String getFullPrefix(){
        return this.prefix.concat(":");
    }

}
