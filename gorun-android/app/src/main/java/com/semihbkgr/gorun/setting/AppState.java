package com.semihbkgr.gorun.setting;

public class AppState {

    private boolean internetConnection;

    private ServerStateType serverStateType;

    public AppState() {
        this.internetConnection=false;
        this.serverStateType=ServerStateType.DOWN;
    }

    public boolean hasInternetConnection() {
        return internetConnection;
    }

    public void setInternetConnection(boolean internetConnection) {
        this.internetConnection = internetConnection;
    }

    public ServerStateType getServerStateType() {
        return serverStateType;
    }

    public void setServerStateType(ServerStateType serverStateType) {
        this.serverStateType = serverStateType;
    }

}
