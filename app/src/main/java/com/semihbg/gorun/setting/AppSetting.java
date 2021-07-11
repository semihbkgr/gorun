package com.semihbg.gorun.setting;

public class AppSetting {

    public final AppState appState;

    public static final AppSetting instance;

    static{
        instance=new AppSetting();
    }

    private AppSetting (){
        this.appState=new AppState();
    }

}
