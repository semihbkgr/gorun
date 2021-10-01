package com.semihbkgr.gorun;

public class AppStatus {

    private final long startUpTimeMs;
    private boolean hasNetworkConnection;
    private boolean isServerUp;

    public AppStatus(long startUpTimeMs) {
        this.startUpTimeMs = startUpTimeMs;
    }

    public long getStartUpTimeMs() {
        return startUpTimeMs;
    }

    public boolean isHasNetworkConnection() {
        return hasNetworkConnection;
    }

    public void setHasNetworkConnection(boolean hasNetworkConnection) {
        this.hasNetworkConnection = hasNetworkConnection;
    }

    public boolean isServerUp() {
        return isServerUp;
    }

    public void setServerUp(boolean serverUp) {
        isServerUp = serverUp;
    }

}
