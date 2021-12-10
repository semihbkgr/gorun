package com.semihbkgr.gorun;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.code.CodeRepository;
import com.semihbkgr.gorun.code.CodeRepositoryImpl;
import com.semihbkgr.gorun.code.CodeService;
import com.semihbkgr.gorun.code.CodeServiceImpl;
import com.semihbkgr.gorun.message.MessageMarshaller;
import com.semihbkgr.gorun.message.MessageMarshallerImpl;
import com.semihbkgr.gorun.run.RunSessionManager;
import com.semihbkgr.gorun.run.RunWebSocketClient;
import com.semihbkgr.gorun.run.RunWebSocketClientImpl;
import com.semihbkgr.gorun.snippet.SnippetService;
import com.semihbkgr.gorun.snippet.SnippetServiceImpl;
import com.semihbkgr.gorun.snippet.client.SnippetClient;
import com.semihbkgr.gorun.snippet.client.SnippetClientImpl;
import com.semihbkgr.gorun.snippet.repository.SnippetRepository;
import com.semihbkgr.gorun.snippet.repository.SnippetRepositoryImpl;
import okhttp3.OkHttpClient;

import java.util.concurrent.*;

public class AppContext {

    private static final String TAG = AppContext.class.getName();

    private static AppContext INSTANCE;

    public final SnippetService snippetService;
    public final AppDatabaseOpenHelper databaseOpenHelper;
    public final AppResourceHelper resourceHelper;
    public final RunSessionManager runSessionManager;
    public final ExecutorService executorService;
    public final ScheduledExecutorService scheduledExecutorService;
    public final CodeService codeService;

    private AppContext(Context context) {
        Gson gson = new GsonBuilder().create();
        OkHttpClient httpClient = new OkHttpClient();
        this.databaseOpenHelper = new AppDatabaseOpenHelper(context);
        this.resourceHelper = new AppResourceHelper(context, gson);
        SnippetClient snippetClient = new SnippetClientImpl(httpClient, gson);
        SnippetRepository snippetRepository = new SnippetRepositoryImpl(databaseOpenHelper.getWritableDatabase());
        this.snippetService = new SnippetServiceImpl(snippetClient, snippetRepository);
        CodeRepository codeRepository = new CodeRepositoryImpl(databaseOpenHelper.getWritableDatabase());
        this.codeService = new CodeServiceImpl(codeRepository);
        if (AppConstants.Values.THREAD_POOL_EXECUTOR_SERVICE_WORKER_THREAD_COUNT == 1)
            this.executorService = Executors.newSingleThreadExecutor(
                    r -> new Thread(r, "AppContextExecutorServiceWorkerThread"));
        else
            this.executorService = Executors.newFixedThreadPool(
                    AppConstants.Values.THREAD_POOL_EXECUTOR_SERVICE_WORKER_THREAD_COUNT,
                    r -> new Thread(r, "AppContextExecutorServiceWorkerThread"));
        if (AppConstants.Values.THREAD_POOL_SCHEDULED_EXECUTOR_SERVICE_WORKER_THREAD_COUNT == 1)
            this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(
                    r -> new Thread(r, "AppContextScheduledExecutorServiceWorkerThread"));
        else
            this.scheduledExecutorService = Executors.newScheduledThreadPool(
                    AppConstants.Values.THREAD_POOL_SCHEDULED_EXECUTOR_SERVICE_WORKER_THREAD_COUNT,
                    r -> new Thread(r, "AppContextScheduledExecutorServiceWorkerThread"));
        MessageMarshaller messageMarshaller = new MessageMarshallerImpl();
        RunWebSocketClient runWebSocketClient = new RunWebSocketClientImpl(httpClient, messageMarshaller);
        this.runSessionManager = new RunSessionManager(runWebSocketClient, executorService);
    }

    public static void initialize(@NonNull Context context) {
        if (INSTANCE != null)
            throw new IllegalStateException("AppContext instance has already been initialized before");
        Log.i(TAG, "initialize: AppContext initialization is being started");
        INSTANCE = new AppContext(context);
        Log.i(TAG, "initialize: AppContext has been initialized");
    }

    public static AppContext instance() {
        if (INSTANCE == null)
            throw new IllegalStateException("AppContext instance has not been initialized yet");
        return INSTANCE;
    }

    public static boolean initialized() {
        return INSTANCE != null;
    }

    public static void execute(@NonNull Runnable r) {
        if (!initialized())
            throw new IllegalStateException("AppContext instance has not been initialized yet");
        INSTANCE.executorService.execute(r);
    }

    public static <T> Future<T> submit(Callable<T> c) {
        if (!initialized())
            throw new IllegalStateException("AppContext instance has not been initialized yet");
        return INSTANCE.executorService.submit(c);
    }

}
