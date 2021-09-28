package com.semihbkgr.gorun.setting;

import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.util.StateUtils;

import java.util.function.Consumer;

public class AppSetting {

    public static final AppSetting instance;

    static {
        instance = new AppSetting();
    }

    public final AppState appState;

    private AppSetting() {
        this.appState = new AppState();
    }

    public void updateAllState() {
        updateInternetConnection();
        updateServerState();
    }

    public void updateInternetConnection() {

    }

    public void updateInternetConnection(Consumer<? super Boolean> callback) {

    }

    public void updateServerState() {

    }

    public void updateServerState(Consumer<? super ServerStateType> callback) {

    }





}
