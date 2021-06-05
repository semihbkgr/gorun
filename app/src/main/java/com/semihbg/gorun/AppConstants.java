package com.semihbg.gorun;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import java.io.File;

public class AppConstants {

    public static final String TAG=AppConstants.class.getName();

    public static final String EXTERNAL_DIR_NAME="GoRun";

    private static File EXTERNAL_DIR_FILE_CACHE;

    public static File createAndGetExternalFile(@NonNull Context context){
        if(EXTERNAL_DIR_FILE_CACHE ==null){
            EXTERNAL_DIR_FILE_CACHE =context.getExternalFilesDir(EXTERNAL_DIR_NAME);
            if(!EXTERNAL_DIR_FILE_CACHE.exists()){
                Log.i(TAG, "getExternalFile: External file is not exist");
                boolean isCreated= EXTERNAL_DIR_FILE_CACHE.mkdirs();
                if(!isCreated)
                    throw new RuntimeException("External file cannot be created");
                Log.i(TAG, "getExternalFile: External file has been created");
            }else
                Log.i(TAG, "getExternalFile: External file has been already created");
        }
        return EXTERNAL_DIR_FILE_CACHE;
    }

}
