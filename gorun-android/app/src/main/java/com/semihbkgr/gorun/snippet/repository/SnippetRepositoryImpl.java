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
                TABLE_NAME, new String[]{Columns.ID_NAME, Columns.TITLE_NAME, Columns.BRIEF_NAME},
                null, null, null, null, null, null)) {
            List<SnippetInfo> snippetInfoList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Columns.ID_NAME));
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE_NAME));
                String brief = cursor.getString(cursor.getColumnIndex(Columns.BRIEF_NAME));
                snippetInfoList.add(new SnippetInfo(id, versionId, order, title, brief));
            }
            return snippetInfoList;
        }
    }

    @Nullable
    @Override
    public Snippet findById(int id) {
        try (Cursor cursor = database.query(
                TABLE_NAME, new String[]{Columns.TITLE_NAME, Columns.BRIEF_NAME, Columns.EXPLANATION_NAME, Columns.CODE_NAME}, Columns.ID_NAME + "=?", new String[]{String.valueOf(id)},
                null, null, null, null)) {
            if (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE_NAME));
                String brief = cursor.getString(cursor.getColumnIndex(Columns.BRIEF_NAME));
                String explanation = cursor.getString(cursor.getColumnIndex(Columns.EXPLANATION_NAME));
                String code = cursor.getString(cursor.getColumnIndex(Columns.CODE_NAME));
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
        database.update(TABLE_NAME, contentValues, Columns.ID_NAME + "=?", new String[]{String.valueOf(snippet.id)});
    }

    @Override
    public void deleteById(int id) {
        database.delete(TABLE_NAME,Columns.ID_NAME + "=?",new String[]{String.valueOf(id)});
    }

    private void putAllSnippetFields(@NonNull ContentValues contentValues, @NonNull Snippet snippet) {
        contentValues.put(Columns.ID_NAME, snippet.id);
        contentValues.put(Columns.TITLE_NAME, snippet.title);
        contentValues.put(Columns.BRIEF_NAME, snippet.brief);
        contentValues.put(Columns.EXPLANATION_NAME, snippet.explanation);
        contentValues.put(Columns.CODE_NAME, snippet.code);
    }

}
