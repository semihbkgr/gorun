package com.semihbkgr.gorun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.semihbkgr.gorun.code.CodeDatabaseConstants;
import com.semihbkgr.gorun.snippet.repository.SnippetDatabaseConstants;

import static com.semihbkgr.gorun.AppConstants.Resources.DATABASE_NAME;
import static com.semihbkgr.gorun.AppConstants.Resources.DATABASE_VERSION;

public class AppDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = AppDatabaseOpenHelper.class.getName();

    public AppDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SnippetDatabaseConstants.SQL_CREATE_TABLE_IF_NOT_EXISTS);
        db.execSQL(CodeDatabaseConstants.SQL_CREATE_TABLE_IF_NOT_EXISTS);
        Log.i(TAG, "onCreate: database table created if not exists, tableName: " + DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SnippetDatabaseConstants.SQL_DROP_TABLE_IF_EXISTS);
        db.execSQL(CodeDatabaseConstants.SQL_DROP_TABLE_IF_EXISTS);
        Log.i(TAG, "onCreate: database table deleted if exists, tableName: " + DATABASE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
