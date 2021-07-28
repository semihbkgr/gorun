package com.semihbg.gorun.tutorial;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import com.semihbg.gorun.core.AppContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalTutorialService implements TutorialService {

    private final SQLiteOpenHelper dbOpenHelper;

    public LocalTutorialService(@NonNull SQLiteOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public long save(@NonNull Subject subject) {
        try (SQLiteDatabase db = AppContext.instance().appDatabaseHelper.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put(TutorialContract.TutorialSubject.COLUMN_NAME_TITLE, subject.getTitle());
            values.put(TutorialContract.TutorialSubject.COLUMN_NAME_DESCRIPTION, subject.getDescription());
            return db.insert(TutorialContract.TutorialSubject.TABLE_NAME, null, values);
        } catch (Throwable t) {
            t.printStackTrace();
            return 0L;
        }
    }

    @Override
    public long saveAll(@NonNull List<? extends Subject> subjectList) {
        try ( SQLiteDatabase db = AppContext.instance().appDatabaseHelper.getWritableDatabase()) {
            db.beginTransaction();
            long insetCount=subjectList.stream()
                    .map(subject -> {
                        ContentValues values = new ContentValues();
                        values.put(TutorialContract.TutorialSubject.COLUMN_NAME_TITLE, subject.getTitle());
                        values.put(TutorialContract.TutorialSubject.COLUMN_NAME_DESCRIPTION, subject.getDescription());
                        return db.insert(TutorialContract.TutorialSubject.TABLE_NAME, null, values);
                    })
                    .mapToInt(count->(int)(long)count)
                    .sum();
            db.setTransactionSuccessful();
            db.endTransaction();
            return insetCount;
        } catch (Throwable t) {
            t.printStackTrace();
            return 0L;
        }
    }

    @Override
    public List<Subject> findSubjects() {
        try (SQLiteDatabase db = AppContext.instance().appDatabaseHelper.getReadableDatabase();
             Cursor cursor = db.query(TutorialContract.TutorialSubject.TABLE_NAME, TutorialContract.TutorialSubject.COLUMN_NAMES, null, null, null, null, null);) {
            List<Subject> subjectList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(TutorialContract.TutorialSubject._ID));
                String title = cursor.getString(cursor.getColumnIndex(TutorialContract.TutorialSubject.COLUMN_NAME_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TutorialContract.TutorialSubject.COLUMN_NAME_DESCRIPTION));
                subjectList.add(new Subject(id, title, description));
            }
            return subjectList;
        } catch (Throwable t) {
            t.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> findLessonsTitle(String subjectName) {
        return null;
    }

    @Override
    public Lesson findLesson(String title) {
        return null;
    }

    @Override
    public long subjectCount() {
        try(SQLiteDatabase db=dbOpenHelper.getReadableDatabase()){
            Cursor  cursor=db.rawQuery("SELECT COUNT(*) FROM"+ TutorialContract.TutorialSubject.TABLE_NAME,null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        }catch (Throwable t){
            t.printStackTrace();
            return 0L;
        }
    }

}
