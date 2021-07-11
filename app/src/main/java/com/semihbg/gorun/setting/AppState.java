package com.semihbg.gorun.setting;

public class AppState {

    private boolean internetConnection;

    private boolean serverConnection;

    private ServerStateType serverStateType;

    public AppState() {
        this.internetConnection=false;
        this.serverConnection=false;
        this.serverStateType=ServerStateType.DOWN;
    }

    public boolean isInternetConnection() {
        return internetConnection;
    }

    public void setInternetConnection(boolean internetConnection) {
        this.internetConnection = internetConnection;
    }

    public boolean isServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(boolean serverConnection) {
        this.serverConnection = serverConnection;
    }

    public ServerStateType getServerStateType() {
        return serverStateType;
    }

    public void setServerStateType(ServerStateType serverStateType) {
        this.serverStateType = serverStateType;
    }

}
