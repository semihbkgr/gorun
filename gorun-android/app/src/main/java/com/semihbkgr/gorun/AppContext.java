package com.semihbkgr.gorun;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.snippet.SnippetClient;
import com.semihbkgr.gorun.snippet.SnippetClientImpl;
import com.semihbkgr.gorun.snippet.SnippetService;
import com.semihbkgr.gorun.snippet.SnippetServiceImpl;
import com.semihbkgr.gorun.snippet.repository.SnippetRepository;
import com.semihbkgr.gorun.snippet.repository.SnippetRepositoryImpl;
import com.semihbkgr.gorun.util.DatabaseHelper;
import com.semihbkgr.gorun.util.ResourceHelper;
import okhttp3.OkHttpClient;

public class AppContext {

    private static final String TAG = AppContext.class.getName();

    private static AppContext instance;

    public final SnippetService snippetService;
    public final ResourceHelper resourceHelper;

    private AppContext(Context context) {

        Gson gson = new GsonBuilder().create();
        OkHttpClient httpClient = new OkHttpClient();
        SnippetClient snippetClient = new SnippetClientImpl(httpClient, gson);

        SQLiteOpenHelper databaseOpenHelper = new DatabaseHelper(context);
        SnippetRepository snippetRepository = new SnippetRepositoryImpl(databaseOpenHelper.getWritableDatabase());

        this.snippetService = new SnippetServiceImpl(snippetClient, snippetRepository);

        this.resourceHelper = new ResourceHelper(context, gson);
    }

    public static void initialize(@NonNull Context context) {
        if (instance != null)
            throw new IllegalStateException("AppContext instance has already been initialized before");
        Log.i(TAG, "initialize: AppContext initialization is being started");
        instance = new AppContext(context);
        Log.i(TAG, "initialize: AppContext has been initialized");
    }

    public static AppContext instance() {
        if (instance == null)
            throw new IllegalStateException("AppContext instance has not been initialized yet");
        return instance;
    }

    public static boolean initialized() {
        return instance != null;
    }

}
