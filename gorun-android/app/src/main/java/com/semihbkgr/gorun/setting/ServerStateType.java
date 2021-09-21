package com.semihbkgr.gorun.setting;

public enum ServerStateType {
    AVAILABLE("available"),
    BUSY("busy"),
    DOWN("down");

    public final String value;

    ServerStateType(String value) {
        this.value = value;
    }

    public static ServerStateType of(String value){
        for(ServerStateType serverStateType:ServerStateType.values()){
            if(serverStateType.value.equalsIgnoreCase(value))
                return serverStateType;
        }
        return DOWN;
    }

}
