package com.semihbkgr.gorun.snippet.repository;

public class SnippetDatabaseConstants {

    public static final String TABLE_NAME = "snippets";

    public static final String SQL_CREATE_TABLE_IF_NOT_EXISTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                    Columns.ID + " INTEGER NOT NULL PRIMARY KEY, " +
                    Columns.VERSION_ID + " INTEGER, " +
                    Columns.ORDER + " INTEGER, " +
                    Columns.TITLE + " TEXT, " +
                    Columns.BRIEF + " TEXT, " +
                    Columns.EXPLANATION + " TEXT, " +
                    Columns.CODE + " TEXT" +
                    " )";

    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static class Columns {

        public static final String ID = "id";
        public static final String VERSION_ID = "version_id";
        public static final String ORDER = "order";
        public static final String TITLE = "title";
        public static final String BRIEF = "brief";
        public static final String EXPLANATION = "explanation";
        public static final String CODE = "code";

    }

}
