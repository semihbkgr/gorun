package com.semihbg.gorun.core;

public class AppConstant {

    //FILE
    public static final String EXTERNAL_DIR_NAME = "GoRun";
    public static final String SUBJECT_ASSET_FILE_NAME = "subject.json";

    //NET
    public static final String SERVER_HTTP_URI = "http://192.168.1.7:8080";
    public static final String SERVER_SOCKET_URI = "ws://192.168.1.7:8080";
    public static final String SERVER_SNIPPET_URI = SERVER_HTTP_URI + "/snippet";
    public static final String SERVER_CODE_RUN_URI = SERVER_SOCKET_URI + "/run";
    public static final String SERVER_CODE_STATE_URI = SERVER_HTTP_URI + "/state";

    public static final String GO_DOC_URL="https://golang.org/pkg/";

    //EXTRA
    public static final String INTENT_EXTRA_SNIPPET_CODE="code";
    public static final String INTENT_EXTRA_SECTION_TITLE="title";

    //VALUE
    public static final long LOGO_SCREEN_TIME_MS=2500L;
    public static final long BACK_BUTTON_TIME_MS=1000L;

}
