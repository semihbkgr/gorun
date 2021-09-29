package com.semihbkgr.gorun;

public class AppConstant {

    //FILE
    public static class File {
        public static final String EXTERNAL_DIR_NAME = "GoRun";
        public static final String TEST_ASSET_FILE_NAME = "test.json";
        public static final String SUBJECT_ASSET_FILE_NAME = "subjects.json";
    }

    public static class Net {
        public static final String SERVER_HTTP_URI = "http://192.168.1.4:8080";
        public static final String SERVER_SOCKET_URI = "ws://192.168.1.7:8080";
        public static final String SERVER_SNIPPET_URI = SERVER_HTTP_URI + "/snippet";
        public static final String SERVER_CODE_RUN_URI = SERVER_SOCKET_URI + "/run";
        public static final String SERVER_CODE_STATE_URI = SERVER_HTTP_URI + "/state";

    }


    public static class Value {
        public static final String INTENT_EXTRA_SNIPPET_CODE = "code";
        public static final String INTENT_EXTRA_SECTION_TITLE = "title";
        public static final String INTENT_SNIPPET_ID_NAME = "snippet_id";
        public static final long LOGO_SCREEN_TIME_MS = 2500L;
        public static final long BACK_BUTTON_TIME_MS = 1000L;
    }


}
