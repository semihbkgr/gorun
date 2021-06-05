package com.semihbg.gorun.snippet;

import android.content.Context;
import android.util.Log;
import com.semihbg.gorun.AppConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultSnippetRepository extends SnippetAbstractRepository {

    public static final String DIR_SNIPPET_NAME="snippets.json";
    private static final String TAG= DefaultSnippetRepository.class.getName();

    private final File file;

    private Snippet[] snippets;

    public DefaultSnippetRepository(Context context) throws IOException {
        super(context);
        File dir=AppConstants.createAndGetExternalDir(context);
        this.file=new File(dir,DIR_SNIPPET_NAME);
        if(!file.exists()){
            Log.i(TAG, "DefaultSnippetRepository: File is not exist");
            if(file.createNewFile()){
                AppConstants.snippetClient.getSnippetAsJsonAsync((json)->{
                    try (FileOutputStream fileOutputStream=new FileOutputStream(file)){
                           fileOutputStream.write(json.getBytes());
                           fileOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }else{
                throw new RuntimeException("DefaultSnippetRepository: File cannot be created");
            }
        }else Log.i(TAG, "DefaultSnippetRepository: File has been already exists");
    }

    @Override
    public List<Snippet> getAllSnippets() {
        if(snippets==null){
            try {
                this.snippets=readSnippets();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Arrays.asList(snippets);
    }

    private Snippet[] readSnippets() throws IOException {
        try(FileInputStream fileInputStream=new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            String json = new String(bytes, StandardCharsets.UTF_8);
            Snippet[] snippets = AppConstants.gson.fromJson(json, Snippet[].class);
            return snippets;
        }
    }

}
