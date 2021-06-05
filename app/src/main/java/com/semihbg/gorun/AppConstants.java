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

public class AppConstants {

    public static final String TAG=AppConstants.class.getName();

    public static final OkHttpClient httpClient=new OkHttpClient();
    public static final SnippetClient snippetClient=new DefaultSnippetClient();
    public static final SnippetRepository snippetRepository=new DefaultSnippetRepository();
    public static final Gson gson=new GsonBuilder().create();

    public static final String EXTERNAL_DIR_NAME="GoRun";
    public static final String SERVER_HTTP_URI ="http://192.168.1.7:8080";
    public static final String SERVER_SOCKET_URI ="ws://192.168.1.7:8080";
    public static final String SERVER_SNIPPET_URI= SERVER_HTTP_URI +"/snippet";
    public static final String SERVER_CODE_RUN_URI= SERVER_SOCKET_URI +"/run";

    private static File EXTERNAL_DIR_CACHE;

    public static File createAndGetExternalDir(@NonNull Context context){
        if(EXTERNAL_DIR_CACHE ==null){
            EXTERNAL_DIR_CACHE =context.getExternalFilesDir(EXTERNAL_DIR_NAME);
            if(!EXTERNAL_DIR_CACHE.exists()){
                Log.i(TAG, "getExternalFile: External dir is not exist");
                boolean isCreated= EXTERNAL_DIR_CACHE.mkdirs();
                if(!isCreated)
                    throw new RuntimeException("External dir cannot be created");
                Log.i(TAG, "getExternalFile: External dir has been created");
            }else
                Log.i(TAG, "getExternalFile: External dir has been already created");
        }
        return EXTERNAL_DIR_CACHE;
    }

    public static void initialize(){

    }


}
