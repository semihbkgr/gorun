package com.semihbkgr.gorun.util;

import com.semihbkgr.gorun.AppConstant;
import com.semihbkgr.gorun.core.AppContext;
import com.semihbkgr.gorun.setting.ServerStateType;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class StateUtils {

    public static boolean hasInternetConnection() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ServerStateType serverStateType() {
        Request request = new Request.Builder()
                .url(AppConstant.Net.SERVER_CODE_STATE_URI)
                .method("GET", null)
                .build();
        try {
            ResponseBody responseBody = AppContext.instance().httpClient.newCall(request).execute().body();
            String responseString = responseBody.string();
            return ServerStateType.of(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerStateType.DOWN;
    }

}
