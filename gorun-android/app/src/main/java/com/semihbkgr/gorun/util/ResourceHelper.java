package com.semihbkgr.gorun.util;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ResourceHelper {

    private final Context context;
    private final Gson gson;

    public ResourceHelper(@NonNull Context context, @NonNull Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    @NonNull
    public String readAsset(@NonNull String fileName) throws IOException {
        try (InputStream is = context.getAssets().open(fileName)) {
            return readStringFromStream(is);
        }
    }

    @NonNull
    public String readStringFromStream(@NonNull InputStream is) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] byteBuffer = new byte[2048];
        int i;
        while ((i = is.read(byteBuffer)) != -1) {
            stringBuilder.append(new String(byteBuffer, 0, i, StandardCharsets.UTF_8));
        }
        return stringBuilder.toString();
    }

    @NonNull
    public <T> T readAsset(@NonNull String fileName, @NonNull Class<T> type) throws IOException {
        try (InputStream is = context.getAssets().open(fileName);
             Reader reader = new InputStreamReader(is)) {
            return readTypeFromReader(reader, type);
        }
    }

    @NonNull
    public <T> T readTypeFromReader(@NonNull Reader reader, @NonNull Class<T> type) {
        return gson.fromJson(reader, type);
    }

}
