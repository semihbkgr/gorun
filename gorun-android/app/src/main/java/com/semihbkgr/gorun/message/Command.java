package com.semihbkgr.gorun.message;

public enum Command {

    //To Response
    START(true),
    END(true),
    INFO(true),
    OUTPUT(true),
    ERROR(true),
    SYSTEM(true),
    //From Request
    RUN(false),
    INPUT(false),
    INTERRUPT(false),
    DISCONNECT(false);

    public final boolean isInResponse;

    Command(boolean isInResponse) {
        this.isInResponse = isInResponse;
    }

    public static Command of(String commandString) throws IllegalArgumentException {
        if (commandString.equalsIgnoreCase(START.name())) return START;
        else if (commandString.equalsIgnoreCase(END.name())) return END;
        else if (commandString.equalsIgnoreCase(INFO.name())) return INFO;
        else if (commandString.equalsIgnoreCase(OUTPUT.name())) return OUTPUT;
        else if (commandString.equalsIgnoreCase(ERROR.name())) return ERROR;
        else if (commandString.equalsIgnoreCase(SYSTEM.name())) return SYSTEM;
        else if (commandString.equalsIgnoreCase(RUN.name())) return RUN;
        else if (commandString.equalsIgnoreCase(INPUT.name())) return INPUT;
        else if (commandString.equalsIgnoreCase(INTERRUPT.name())) return INTERRUPT;
        else if (commandString.equalsIgnoreCase(DISCONNECT.name())) return DISCONNECT;
        else throw new IllegalArgumentException(String.format("Illegal command parameter, parameter : %s", commandString));
    }

    public static Command of(String commandString, boolean isInResponse) throws IllegalArgumentException {
        if (isInResponse == START.isInResponse && commandString.equalsIgnoreCase(START.name())) return START;
        else if (isInResponse == END.isInResponse && commandString.equalsIgnoreCase(END.name())) return END;
        else if (isInResponse == INFO.isInResponse && commandString.equalsIgnoreCase(INFO.name())) return INFO;
        else if (isInResponse == OUTPUT.isInResponse && commandString.equalsIgnoreCase(OUTPUT.name())) return OUTPUT;
        else if (isInResponse == ERROR.isInResponse && commandString.equalsIgnoreCase(ERROR.name())) return ERROR;
        else if (isInResponse == SYSTEM.isInResponse && commandString.equalsIgnoreCase(SYSTEM.name())) return SYSTEM;
        else if (isInResponse == RUN.isInResponse && commandString.equalsIgnoreCase(RUN.name())) return RUN;
        else if (isInResponse == INPUT.isInResponse && commandString.equalsIgnoreCase(INPUT.name())) return INPUT;
        else if (isInResponse == INTERRUPT.isInResponse && commandString.equalsIgnoreCase(INTERRUPT.name())) return INTERRUPT;
        else if (isInResponse == DISCONNECT.isInResponse && commandString.equalsIgnoreCase(DISCONNECT.name())) return DISCONNECT;
        else throw new IllegalArgumentException(String.format("Illegal command parameter, parameter : %s", commandString));
    }


}
