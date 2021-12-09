package com.semihbkgr.gorun;

public class AppConstants {

    public static class Resources {
        public static final String TEST_ASSET_FILE_NAME = "test.json";
        public static final String DATABASE_NAME = "gorun.db";
        public static final int DATABASE_VERSION = 1;
    }

    public static class Nets {
        public static final String SERVER_HTTP_URI = "http://192.168.1.108:8080";
        public static final String SERVER_WEB_SOCKET_URI = "ws://192.168.1.108:8080";
        public static final String SERVER_SNIPPET_HTTP_URI = SERVER_HTTP_URI + "/snippet";
        public static final String SERVER_RUN_WEB_SOCKET_URI = SERVER_WEB_SOCKET_URI + "/run";
    }

    public static class Values {
        public static final String INTENT_SNIPPET_ID_NAME = "snippet-id";
        public static final String INTENT_SNIPPET_CODE_NAME = "snippet-name";
        public static final String DIALOG_PROPERTY_CODE_CONTENT = "code";
        public static final long LOGO_ON_SCREEN_TIME_MS = 25L;
        public static final long BACK_BUTTON_TIME_MS = 1000L;
        public static final long CACHE_EXPIRED_CLEAR_TIME_INTERVAL_MS = 60_000L;
        public static final int THREAD_POOL_EXECUTOR_SERVICE_WORKER_THREAD_COUNT = 5;
        public static final int THREAD_POOL_SCHEDULED_EXECUTOR_SERVICE_WORKER_THREAD_COUNT = 1;
    }

}
