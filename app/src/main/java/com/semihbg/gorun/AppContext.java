package com.semihbg.gorun;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbg.gorun.snippet.DefaultSnippetClient;
import com.semihbg.gorun.snippet.DefaultSnippetRepository;
import com.semihbg.gorun.snippet.SnippetClient;
import com.semihbg.gorun.snippet.SnippetRepository;
import okhttp3.OkHttpClient;

import java.io.File;

import static com.semihbg.gorun.AppConstants.EXTERNAL_DIR_NAME;

public class AppContext {

    public static final String TAG = AppContext.class.getName();

    //Singleton service and repository's instances
    public static OkHttpClient httpClient;
    public static Gson gson;
    public static File rootDir;
    public static SnippetClient snippetClient;
    public static SnippetRepository snippetRepository;

    private static boolean isInitialized=false;

    public static void initialize(@NonNull Context context) {
        if(!isInitialized){
            isInitialized=true;
            Log.i(TAG, "initialize: AppContext has been starting initialization");
            httpClient=new OkHttpClient();
            gson=new GsonBuilder().create();
            rootDir=createAndGetExternalDir(context);
            snippetClient=new DefaultSnippetClient(httpClient,gson);
            snippetRepository=new DefaultSnippetRepository(context,snippetClient,gson,rootDir);
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
