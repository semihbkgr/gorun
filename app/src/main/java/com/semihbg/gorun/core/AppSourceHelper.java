package com.semihbg.gorun.core;

import android.content.Context;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class AppSourceHelper {

    private final Context context;
    private final Gson gson;

    public AppSourceHelper(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public String readAsset(String fileName) throws IOException {
        try (InputStream is = context.getAssets().open(fileName)) {
            StringBuilder stringBuilder = new StringBuilder();
            byte[] byteBuffer = new byte[2048];
            int i;
            while ((i = is.read(byteBuffer)) != -1) {
                stringBuilder.append(new String(byteBuffer, 0, i, StandardCharsets.UTF_8));
            }
            return stringBuilder.toString();
        }
    }

    public <T> T readAsset(String fileName, Class<T> type) throws IOException {
        try (InputStream is = context.getAssets().open(fileName);
             Reader reader = new InputStreamReader(is)) {
            return gson.fromJson(reader, type);
        }
    }

}
