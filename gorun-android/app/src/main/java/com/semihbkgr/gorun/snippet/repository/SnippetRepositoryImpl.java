package com.semihbkgr.gorun.snippet.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
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
    public List<SnippetInfo> getSnippetInfos() {
        try (Cursor cursor = database.query(TABLE_NAME, new String[]{Columns.ID_NAME, Columns.TITLE_NAME, Columns.BRIEF_NAME},
                null, null, null, null, null, null)) {
            List<SnippetInfo> snippetInfoList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Columns.ID_NAME));
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE_NAME));
                String brief = cursor.getString(cursor.getColumnIndex(Columns.BRIEF_NAME));
                snippetInfoList.add(new SnippetInfo(id, title, brief));
            }
            return snippetInfoList;
        }
    }

    @Override
    public Snippet getSnippet(int id) {
        return null;
    }

    @Override
    public void saveSnippet(Snippet snippet) {

    }

    @Override
    public void updateSnippet(int id, Snippet snippet) {

    }

    @Override
    public void deleteSnippet(int id) {

    }

}
