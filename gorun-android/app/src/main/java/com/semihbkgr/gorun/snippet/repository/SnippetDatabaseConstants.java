package com.semihbkgr.gorun.snippet.repository;

public class SnippetDatabaseConstants {

    public static final String TABLE_NAME = "snippets";

    public static final String SQL_CREATE_TABLE_IF_NOT_EXISTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                    Columns.ID_NAME + " INTEGER NOT NULL PRIMARY KEY, " +
                    Columns.TITLE_NAME + " TEXT NOT NULL, " +
                    Columns.BRIEF_NAME + " TEXT NOT NULL, " +
                    Columns.EXPLANATION_NAME + " TEXT NOT NULL, " +
                    Columns.CODE_NAME + " TEXT NOT NULL " +
                    " )";

    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static class Columns {

        public static final String ID_NAME = "id";
        public static final String TITLE_NAME = "title";
        public static final String BRIEF_NAME = "brief";
        public static final String EXPLANATION_NAME = "explanation";
        public static final String CODE_NAME = "code";

    }

}
