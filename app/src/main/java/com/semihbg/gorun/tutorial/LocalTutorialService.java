package com.semihbg.gorun.tutorial;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import com.semihbg.gorun.core.AppContext;

import java.util.ArrayList;
import java.util.List;

import static com.semihbg.gorun.tutorial.TutorialContract.TutorialSubject.*;

public class LocalTutorialService implements TutorialService {

    private final SQLiteOpenHelper dbOpenHelper;

    public LocalTutorialService(@NonNull SQLiteOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public List<Subject> findSubjects() {
        SQLiteDatabase db = AppContext.instance().appDatabaseHelper.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME, COLUMN_NAMES, null,null,null,null,null);
        List<Subject> subjectList=new ArrayList<>();
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(_ID));
            String title=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE));
            String description=cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
            subjectList.add(new Subject(id,title,description));
        }
        return subjectList;
    }

    @Override
    public List<String> findLessonsTitle(String subjectName) {
        return null;
    }

    @Override
    public Lesson findLesson(String title) {
        return null;
    }

}
