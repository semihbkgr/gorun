package com.semihbkgr.gorun.snippet;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.semihbkgr.gorun.core.AppConstant;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class DefaultSnippetClient implements SnippetClient {

    private static final String TAG = DefaultSnippetClient.class.getName();

    private final OkHttpClient httpClient;
    private final Gson gson;

    public DefaultSnippetClient(OkHttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Nullable
    @Override
    public SnippetInfo[] getAllSnippetInfos() throws IOException {
        Request request = new Request.Builder()
                .url(AppConstant.SERVER_SNIPPET_URI)
                .build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        try (ResponseBody body = response.body()) {
            if (body != null)
                return gson.fromJson(response.body().string(), SnippetInfo[].class);
        }catch (Exception e){
            Log.e(TAG, "getAllSnippetInfos: ",e);
            return null;
        }
    }

    @Override
    public void getAllSnippetInfosAsync(Consumer<? super SnippetInfo[]> snippetArrayConsumer) {
        Request request = new Request.Builder().url(AppConstant.SERVER_SNIPPET_URI).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: AllSnippetInfos request failed", e);
                snippetArrayConsumer.accept(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "onResponse: AllSnippetInfos response received successfully");
                try {
                    SnippetInfo[] snippetInfos = gson.fromJson(response.body().string(), SnippetInfo[].class);
                    Log.i(TAG, "onResponse: AllSnippetInfos response deserialized successfully");
                    snippetArrayConsumer.accept(snippetInfos);
                } catch (Throwable e) {
                    Log.e(TAG, "onResponse: AllSnippetInfos response processing failed", e);
                    snippetArrayConsumer.accept(null);
                }
            }
        });
    }

    @Override
    public Future<SnippetInfo[]> getAllSnippetInfoFuture() {
        return CompletableFuture.supplyAsync(()->{
            try {
                return getAllSnippetInfos();
            } catch (IOException e) {
                Log.e(TAG, "getAllSnippetInfoFuture: ", e);
                return new SnippetInfo[0];
            }
        });
    }

    @Override
    public Snippet getSnippet(int id) throws IOException {
        String url=AppConstant.SERVER_SNIPPET_URI+'/'+id;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClient.newCall(request);
        Response response=call.execute();
        try(ResponseBody body=response.body()){
            if(body!=null)
                return gson.fromJson(response.body().string(), Snippet.class);
            return null;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void getSnippetAsync(int id, Consumer<? super SnippetInfo> callback) {

    }

    @Override
    public Future<Snippet> getSnippetFuture(int id) {
        return null;
    }


}
