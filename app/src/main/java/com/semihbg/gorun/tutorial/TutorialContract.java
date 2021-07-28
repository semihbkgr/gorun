package com.semihbg.gorun.tutorial;

import android.provider.BaseColumns;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbg.gorun.core.AppContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TutorialContract {

    static final String SUBJECT_ASSET_FILE_NAME = "subject.json";

    public static class TutorialSubject implements BaseColumns {

        public static final String TABLE_NAME = "subjects";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TITLE + " TEXT," +
                        COLUMN_NAME_DESCRIPTION + " TEXT)";

        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_COUNT =
                "SELECT COUNT(*) FROM" + TABLE_NAME;

        @Nullable
        public static String getSqlSave(@NonNull AppContext appContext) {
            try (InputStream inputStream = appContext.context.getAssets().open(SUBJECT_ASSET_FILE_NAME);
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);) {
                Subject[] subjects = appContext.gson.fromJson(inputStreamReader, Subject[].class);
                StringBuilder stringBuilder = new StringBuilder("INSERT INTO " + TABLE_NAME + " (id,title,description) VALUES ");
                int id = 0;
                for (Subject subject : subjects) {
                    stringBuilder.append("(")
                            .append(++id)
                            .append(",")
                            .append(subject.getTitle())
                            .append(",")
                            .append(subject.getDescription())
                            .append(")")
                            .append(",");
                }
                stringBuilder.append("\b");
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
