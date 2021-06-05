package com.semihbg.gorun.snippet;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.semihbg.gorun.util.ThrowUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DefaultSnippetRepository extends SnippetAbstractRepository {

    public static final String DIR_SNIPPET_NAME="snippets.json";
    private static final String TAG= DefaultSnippetRepository.class.getName();

    private final SnippetClient snippetClient;
    private final Gson gson;
    private final Handler handler;
    private final File file;
    private Snippet[] snippetsCache;

    public DefaultSnippetRepository(Context context,SnippetClient snippetClient,Gson gson,File dir) {
        super(context);
        if(!dir.exists())
            throw new IllegalStateException("Root dir is not exists");
        this.snippetClient=snippetClient;
        this.gson=gson;
        this.file=new File(dir,DIR_SNIPPET_NAME);
        this.handler=new Handler();
        if(!file.exists()){
            Log.i(TAG, "DefaultSnippetRepository: File is not exist");
            try {
                if(file.createNewFile()){
                    Log.i(TAG, "DefaultSnippetRepository: File has been created");
                }else{
                    throw new RuntimeException("DefaultSnippetRepository: File cannot be created");
                }
            } catch (IOException e) {
                ThrowUtils.sneakyThrow(e);
            }
        }else Log.i(TAG, "DefaultSnippetRepository: File has been already exists");
    }

    @Override
    public List<Snippet> getAllSnippetsBlock() {
        if(snippetsCache ==null){
            Snippet[] snippets = readSnippets();
            if(snippets==null)
                snippets=snippetClient.getSnippetsBlock();
            this.snippetsCache=snippets;
        }
        return Arrays.asList(snippetsCache);
    }

    @Override
    public void getAllSnippetsAsync(Consumer<? super List<Snippet>> consumerSnippetList) {
        handler.post(()->{
            Snippet[] snippets = readSnippets();
            if(snippets!=null)
                consumerSnippetList.accept(Arrays.asList(snippets));
            else
                snippetClient.getSnippetAsync((s)-> consumerSnippetList.accept(Arrays.asList(s)));
        });
    }

    @Override
    public boolean isCached(){
        return snippetsCache!=null;
    }

    //Return null when occurs error
    @Nullable
    private Snippet[] readSnippets() {
        try(FileInputStream fileInputStream=new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            String json = new String(bytes, StandardCharsets.UTF_8);
            Snippet[] snippets = gson.fromJson(json, Snippet[].class);
            return snippets;
        }catch (Exception ignore){}
        return null;
    }

}
