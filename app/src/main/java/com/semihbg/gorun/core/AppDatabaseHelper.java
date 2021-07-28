package com.semihbg.gorun.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.semihbg.gorun.tutorial.TutorialContract;
import com.semihbg.gorun.tutorial.TutorialContract.TutorialSubject;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="gorun.db";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Cursor tutorialCursor= db.rawQuery(TutorialSubject.SQL_COUNT, null);
        tutorialCursor.moveToFirst();
        int tutorialCount= tutorialCursor.getInt(0);
        if(tutorialCount==0){

        }
        tutorialCursor.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TutorialSubject.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TutorialSubject.SQL_DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

}
