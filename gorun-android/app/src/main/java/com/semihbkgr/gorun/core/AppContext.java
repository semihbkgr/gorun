package com.semihbkgr.gorun.core;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.snippet.SnippetClient;
import com.semihbkgr.gorun.snippet.SnippetClientImpl;
import com.semihbkgr.gorun.snippet.SnippetService;
import com.semihbkgr.gorun.snippet.SnippetServiceImpl;
import com.semihbkgr.gorun.util.ListenedThreadPoolWrapper;
import okhttp3.OkHttpClient;

import java.io.File;

import static com.semihbkgr.gorun.core.AppConstant.EXTERNAL_DIR_NAME;

public class AppContext {

    private static final String TAG = AppContext.class.getName();

    private static AppContext instance;
    public final Context context;
    public final OkHttpClient httpClient;
    public final Gson gson;
    public final File rootDir;
    public final SnippetClient snippetClient;
    public final SnippetService snippetService;
    public final AppDatabaseHelper appDatabaseHelper;
    public final AppSourceHelper appSourceHelper;
    //TODO thread pool termination
    public final ListenedThreadPoolWrapper listenedThreadPoolWrapper;

    private AppContext(Context context) {
        this.context = context;
        this.httpClient = new OkHttpClient();
        this.gson = new GsonBuilder().create();
        this.rootDir = createAndGetExternalDir(context);
        this.snippetClient = new SnippetClientImpl(httpClient, gson);
        this.snippetService = new SnippetServiceImpl(snippetClient);
        this.appDatabaseHelper = new AppDatabaseHelper(context);
        this.appSourceHelper = new AppSourceHelper(context, gson);
        this.listenedThreadPoolWrapper = new ListenedThreadPoolWrapper(5);
    }

    public static void initialize(Context context) {
        if (instance != null)
            throw new IllegalStateException("AppContext instance has already been initialized before");
        if (context == null)
            throw new IllegalArgumentException("Context parameter cannot be null");
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

    @Nullable
    private File createAndGetExternalDir(@NonNull Context context) {
        File dir = context.getExternalFilesDir(EXTERNAL_DIR_NAME);
        if (dir == null) return null;
        if (!dir.exists()) {
            Log.i(TAG, "createAndGetExternalDir: Root dir is not exist");
            boolean isCreated = dir.mkdirs();
            if (!isCreated) throw new IllegalStateException("Root dir cannot be created");
            Log.i(TAG, "createAndGetExternalDir: Root dir has been created");
        } else Log.i(TAG, "createAndGetExternalDir: Root dir has been already created");
        return dir;
    }

}
