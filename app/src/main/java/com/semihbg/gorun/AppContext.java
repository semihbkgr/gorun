package com.semihbg.gorun;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbg.gorun.snippet.*;
import com.semihbg.gorun.tutorial.LocalTutorialService;
import com.semihbg.gorun.tutorial.TutorialService;
import com.semihbg.gorun.util.ListenedThreadPoolWrapper;
import okhttp3.OkHttpClient;

import java.io.File;

import static com.semihbg.gorun.AppConstants.EXTERNAL_DIR_NAME;

public class AppContext {

    public static final String TAG = AppContext.class.getName();

    //TODO make app context singleton and static to class fields

    //Singleton service and repository's instances
    public static OkHttpClient httpClient;
    public static Gson gson;
    public static File rootDir;
    public static SnippetClient snippetClient;
    public static SnippetService snippetService;
    public static TutorialService tutorialService;
    //TODO thread pool termination
    public static ListenedThreadPoolWrapper listenedThreadPoolWrapper;

    private static boolean isInitialized=false;

    public static void initialize(@NonNull Context context) {
        if(!isInitialized){
            isInitialized=true;
            Log.i(TAG, "initialize: AppContext has been starting initialization");
            httpClient=new OkHttpClient();
            gson=new GsonBuilder().create();
            rootDir=createAndGetExternalDir(context);
            snippetClient=new DefaultSnippetClient(httpClient,gson);
            snippetService=new DefaultSnippetService(snippetClient);
            tutorialService=new LocalTutorialService(context,gson);
            listenedThreadPoolWrapper=new ListenedThreadPoolWrapper(5);
            Log.i(TAG, "initialize: AppContext has been ending initialization");
        }else Log.w(TAG, "initialize: AppContext has already been initialized");
    }

    public static File createAndGetExternalDir(@NonNull Context context){
        File dir =context.getExternalFilesDir(EXTERNAL_DIR_NAME);
        if(!dir.exists()){
            Log.i(TAG, "createAndGetExternalDir: Root dir is not exist");
            boolean isCreated= dir.mkdirs();
            if(!isCreated)
                throw new RuntimeException("Root dir cannot be created");
            Log.i(TAG, "createAndGetExternalDir: Root dir has been created");
        }else Log.i(TAG, "createAndGetExternalDir: Root dir has been already created");
        return dir;
    }



}
