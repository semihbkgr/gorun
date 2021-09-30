package com.semihbkgr.gorun.snippet.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;

import java.util.ArrayList;
import java.util.List;

import static com.semihbkgr.gorun.snippet.repository.SnippetDatabaseConstants.Columns;
import static com.semihbkgr.gorun.snippet.repository.SnippetDatabaseConstants.TABLE_NAME;

public class SnippetRepositoryImpl implements SnippetRepository {

    private final SQLiteDatabase database;

    public SnippetRepositoryImpl(@NonNull SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public List<SnippetInfo> findAll() {
        try (Cursor cursor = database.query(
                TABLE_NAME, new String[]{Columns.ID, Columns.VERSION_ID, Columns.ORDER, Columns.TITLE, Columns.BRIEF},
                null, null, null, null, null, null)) {
            List<SnippetInfo> snippetInfoList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Columns.ID));
                int versionId = cursor.getInt(cursor.getColumnIndex(Columns.VERSION_ID));
                int order = cursor.getInt(cursor.getColumnIndex(Columns.ORDER));
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
                String brief = cursor.getString(cursor.getColumnIndex(Columns.BRIEF));
                snippetInfoList.add(new SnippetInfo(id, versionId, order, title, brief));
            }
            return snippetInfoList;
        }
    }

    @Override
    public List<Integer> findAllId() {
        try(Cursor cursor = database.query(
                TABLE_NAME, new String[]{Columns.ID},
                null, null, null, null, null, null)){
            List<Integer> idList = new ArrayList<>();
            while (cursor.moveToNext())
                idList.add(cursor.getInt(cursor.getColumnIndex(Columns.ID)));
            return idList;
        }
    }

    @Nullable
    @Override
    public Snippet findById(int id) {
        try (Cursor cursor = database.query(
                TABLE_NAME, new String[]{Columns.VERSION_ID,Columns.ORDER,Columns.TITLE, Columns.BRIEF, Columns.EXPLANATION, Columns.CODE}, Columns.ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null)) {
            if (cursor.moveToNext()) {
                int versionId = cursor.getInt(cursor.getColumnIndex(Columns.VERSION_ID));
                int order = cursor.getInt(cursor.getColumnIndex(Columns.ORDER));
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
                String brief = cursor.getString(cursor.getColumnIndex(Columns.BRIEF));
                String explanation = cursor.getString(cursor.getColumnIndex(Columns.EXPLANATION));
                String code = cursor.getString(cursor.getColumnIndex(Columns.CODE));
                return new Snippet(id, versionId, order, title, brief, explanation, code);
            } else
                return null;
        }
    }

    @Override
    public void save(Snippet snippet) {
        ContentValues contentValues = new ContentValues();
        putAllSnippetFields(contentValues, snippet);
        database.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void update(Snippet snippet) {
        ContentValues contentValues = new ContentValues();
        putAllSnippetFields(contentValues, snippet);
        database.update(TABLE_NAME, contentValues, Columns.ID + "=?", new String[]{String.valueOf(snippet.id)});
    }

    @Override
    public void deleteById(int id) {
        database.delete(TABLE_NAME, Columns.ID + "=?", new String[]{String.valueOf(id)});
    }

    private void putAllSnippetFields(@NonNull ContentValues contentValues, @NonNull Snippet snippet) {
        contentValues.put(Columns.ID, snippet.id);
        contentValues.put(Columns.TITLE, snippet.title);
        contentValues.put(Columns.BRIEF, snippet.brief);
        contentValues.put(Columns.EXPLANATION, snippet.explanation);
        contentValues.put(Columns.CODE, snippet.code);
    }

}
