package com.semihbkgr.gorun.code;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.semihbkgr.gorun.code.CodeDatabaseConstants.Columns;
import static com.semihbkgr.gorun.code.CodeDatabaseConstants.TABLE_NAME;

public class CodeRepositoryImpl implements CodeRepository {

    private final SQLiteDatabase database;

    public CodeRepositoryImpl(@NonNull SQLiteDatabase database) {
        this.database = database;
    }

    @Nullable
    @Override
    public Code findById(int id) {
        try (Cursor cursor = database.query(
                TABLE_NAME, new String[]{Columns.TITLE, Columns.CONTENT, Columns.CREATED_AT, Columns.UPDATED_AT},
                Columns.ID + "=?", new String[]{String.valueOf(id)}, null, null, null)) {
            if (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
                String content = cursor.getString(cursor.getColumnIndex(Columns.CONTENT));
                long createdAt = cursor.getLong(cursor.getColumnIndex(Columns.CREATED_AT));
                long updatedAt = cursor.getLong(cursor.getColumnIndex(Columns.UPDATED_AT));
                return new Code(id, title, content, createdAt, updatedAt);
            } else
                return null;
        }
    }

    @Override
    public List<CodeInfo> findAllInfos() {
        try (Cursor cursor = database.query(
                TABLE_NAME, new String[]{Columns.ID, Columns.TITLE, Columns.CREATED_AT, Columns.UPDATED_AT},
                null, null, null, null, null)) {
            List<CodeInfo> codeInfoList = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Columns.ID));
                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
                long createdAt = cursor.getLong(cursor.getColumnIndex(Columns.CREATED_AT));
                long updatedAt = cursor.getLong(cursor.getColumnIndex(Columns.UPDATED_AT));
                codeInfoList.add(new CodeInfo(id, title, createdAt, updatedAt));
            }
            return codeInfoList;
        }
    }

    @Override
    public Code save(Code code) {
        ContentValues contentValues = new ContentValues();
        putAllCodeFields(contentValues, code);
        long id = database.insert(TABLE_NAME, null, contentValues);
        code.setId((int) id);
        return code;
    }

    @Override
    public Code update(int id, Code code) {
        code.setId(id);
        ContentValues contentValues = new ContentValues();
        putAllCodeFields(contentValues, code);
        database.update(TABLE_NAME, contentValues, Columns.ID + "=?", new String[]{String.valueOf(id)});
        return code;
    }

    @Override
    public int delete(int id) {
        return database.delete(TABLE_NAME, Columns.ID + "=?", new String[]{String.valueOf(id)});
    }

    private void putAllCodeFields(@NonNull ContentValues contentValues, @NonNull Code code) {
        contentValues.put(Columns.ID, code.getId());
        contentValues.put(Columns.TITLE, code.getTitle());
        contentValues.put(Columns.CONTENT, code.getContent());
        contentValues.put(Columns.CREATED_AT, code.getCreatedAt());
        contentValues.put(Columns.UPDATED_AT, code.getUpdatedAt());
    }

}
