package com.semihbkgr.gorun;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.snippet.SnippetClient;
import com.semihbkgr.gorun.snippet.SnippetClientImpl;
import com.semihbkgr.gorun.snippet.SnippetService;
import com.semihbkgr.gorun.snippet.SnippetServiceImpl;
import com.semihbkgr.gorun.util.DatabaseHelper;
import com.semihbkgr.gorun.util.ResourceHelper;
import okhttp3.OkHttpClient;

import java.io.File;

import static com.semihbkgr.gorun.AppConstant.File.EXTERNAL_DIR_NAME;

public class AppContext {

    private static final String TAG = AppContext.class.getName();

    private static AppContext instance;


    public final File rootDir;
    public final Gson gson;
    public final OkHttpClient httpClient;
    public final SnippetService snippetService;
    public final DatabaseHelper databaseHelper;
    public final ResourceHelper resourceHelper;

    private AppContext(Context context) {

        this.rootDir = context.getExternalFilesDir(EXTERNAL_DIR_NAME);
        if (rootDir!=null && !rootDir.exists()) {
            Log.i(TAG, "createAndGetExternalDir: Root dir is not exist");
            boolean isCreated = rootDir.mkdirs();
            if (!isCreated) throw new IllegalStateException("Root dir cannot be created");
            Log.i(TAG, "createAndGetExternalDir: Root dir has been created");
        } else Log.i(TAG, "createAndGetExternalDir: Root dir has been already created");

        this.gson = new GsonBuilder().create();
        this.httpClient = new OkHttpClient();

        SnippetClient snippetClient = new SnippetClientImpl(httpClient, gson);
        this.snippetService = new SnippetServiceImpl(snippetClient);

        this.databaseHelper = new DatabaseHelper(context);
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
