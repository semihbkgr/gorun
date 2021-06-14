package com.semihbg.gorun;

public class AppConstants {

    public static final String EXTERNAL_DIR_NAME = "GoRun";

    public static final String INTENT_EXTRA_SNIPPET_CODE="code";

    public static final String SERVER_HTTP_URI = "http://192.168.1.7:8080";
    public static final String SERVER_SOCKET_URI = "ws://192.168.1.7:8080";
    public static final String SERVER_SNIPPET_URI = SERVER_HTTP_URI + "/snippet";
    public static final String SERVER_CODE_RUN_URI = SERVER_SOCKET_URI + "/run";



}