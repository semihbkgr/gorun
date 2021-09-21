package com.semihbkgr.gorun.tutorial;

import android.provider.BaseColumns;

public class TutorialContract {


    public static class TutorialSubject implements BaseColumns {

        public static final String TABLE_NAME = "subjects";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

        public static final String[] COLUMN_NAMES=new String[]
                {_ID,COLUMN_NAME_TITLE,COLUMN_NAME_DESCRIPTION};

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TITLE + " TEXT," +
                        COLUMN_NAME_DESCRIPTION + " TEXT)";

        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_COUNT =
                "SELECT COUNT(*) FROM" + TABLE_NAME;


    }

}
