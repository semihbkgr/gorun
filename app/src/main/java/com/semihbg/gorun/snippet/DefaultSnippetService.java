package com.semihbg.gorun.snippet;

import android.content.Context;
import android.util.Log;
import com.semihbg.gorun.AppConstants;

import java.io.File;
import java.util.List;

public class DefaultSnippetService extends SnippetServiceAbstract {

    public static final String DIR_SNIPPET_NAME="snippets";
    private static final String TAG=DefaultSnippetService.class.getName();

    private final File file;

    public DefaultSnippetService(Context context) {
        super(context);
        File rootFile=AppConstants.createAndGetExternalFile(context);
        this.file=new File(rootFile,DIR_SNIPPET_NAME);
        if(!file.exists()){
            Log.i(TAG, "DefaultSnippetService: File is not exist");

        }else Log.i(TAG, "DefaultSnippetService: File has been already exists");
    }

    @Override
    public List<Snippet> getAllSnippets() {
        return null;
    }

}
