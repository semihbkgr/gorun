package com.semihbkgr.gorun.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.semihbkgr.gorun.snippet.repository.SnippetDatabaseConstants;

import static com.semihbkgr.gorun.snippet.repository.SnippetDatabaseConstants.*;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG=DatabaseHelper.class.getName();

    static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="gorun.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_IF_NOT_EXISTS);
        Log.i(TAG, "onCreate: database table created if not exists, tableName: "+DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_IF_EXISTS);
        Log.i(TAG, "onCreate: database table deleted if exists, tableName: "+DATABASE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

}
