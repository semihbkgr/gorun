package com.semihbkgr.gorun.code;

public class CodeDatabaseConstants {

    public static final String TABLE_NAME = "codex";

    public static final String SQL_CREATE_TABLE_IF_NOT_EXISTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                    Columns.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    Columns.TITLE + " TEXT, " +
                    Columns.CONTENT + " TEXT, " +
                    Columns.CREATED_AT + " BIG INTEGER, " +
                    Columns.UPDATED_AT + " BIG INTEGER" +
                    " )";

    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static class Columns {

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";

    }

}
