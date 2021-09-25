package com.semihbkgr.gorun.server.message;

public enum Command {

    //From Request
    RUN(false),
    INPUT(false),
    INTERRUPT(false),
    //To Response
    OUTPUT(true),
    INFO(true),
    WARN(true),
    SYSTEM(true);

    public final boolean isInResponse;

    Command(boolean isInResponse) {
        this.isInResponse = isInResponse;
    }

    public static Command of(String commandString) throws IllegalArgumentException {
        if (commandString.equalsIgnoreCase(RUN.name())) return RUN;
        else if (commandString.equalsIgnoreCase(INPUT.name())) return INPUT;
        else if (commandString.equalsIgnoreCase(INTERRUPT.name())) return INTERRUPT;
        else if (commandString.equalsIgnoreCase(INFO.name())) return INFO;
        else if (commandString.equalsIgnoreCase(WARN.name())) return WARN;
        else if (commandString.equalsIgnoreCase(OUTPUT.name())) return OUTPUT;
        else if (commandString.equalsIgnoreCase(SYSTEM.name())) return SYSTEM;
        else throw new IllegalArgumentException(String.format("Illegal command parameter, parameter : %s", commandString));
    }

    public static Command of(String commandString, boolean isInResponse) throws IllegalArgumentException {
        if (isInResponse == RUN.isInResponse && commandString.equalsIgnoreCase(RUN.name())) return RUN;
        else if (isInResponse == INPUT.isInResponse && commandString.equalsIgnoreCase(INPUT.name())) return INPUT;
        else if (isInResponse == INTERRUPT.isInResponse && commandString.equalsIgnoreCase(INTERRUPT.name())) return INTERRUPT;
        else if (isInResponse == INFO.isInResponse && commandString.equalsIgnoreCase(INFO.name())) return INFO;
        else if (isInResponse == WARN.isInResponse && commandString.equalsIgnoreCase(INFO.name())) return WARN;
        else if (isInResponse == OUTPUT.isInResponse && commandString.equalsIgnoreCase(OUTPUT.name())) return OUTPUT;
        else if (isInResponse == SYSTEM.isInResponse && commandString.equalsIgnoreCase(SYSTEM.name())) return SYSTEM;
        else throw new IllegalArgumentException(String.format("Illegal command parameter, parameter : %s", commandString));
    }

}
