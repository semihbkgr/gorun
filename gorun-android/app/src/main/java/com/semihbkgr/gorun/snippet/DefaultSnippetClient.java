package com.semihbkgr.gorun.snippet;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.semihbkgr.gorun.core.AppConstant;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
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
    public Snippet[] getSnippetsBlock() {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Snippet[]> snippetArrayReference = new AtomicReference<>();
        Request request = new Request.Builder().url(AppConstant.SERVER_SNIPPET_URI).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: Snippet request is fail", e);
                latch.countDown();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "onResponse: Snippet response is successful");
                Snippet[] snippets = gson.fromJson(response.body().string(), Snippet[].class);
                snippetArrayReference.set(snippets);
                Log.i(TAG, "onResponse: Snippet response deserialized successfully");
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return snippetArrayReference.get();
    }

    @Override
    public void getSnippetAsync(Consumer<? super Snippet[]> snippetArrayConsumer) {
        Request request = new Request.Builder().url(AppConstant.SERVER_SNIPPET_URI).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: Snippet request is fail", e);
                snippetArrayConsumer.accept(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "onResponse: Snippet response deserialized successfully");
                Snippet[] snippets = gson.fromJson(response.body().string(), Snippet[].class);
                Log.i(TAG, "onResponse: Snippet response deserialized successfully");
                snippetArrayConsumer.accept(snippets);
            }
        });
    }

    @Override
    public String getSnippetAsJsonBlock() {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> stringReference = new AtomicReference<>();
        Request request = new Request.Builder().url(AppConstant.SERVER_SNIPPET_URI).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: Snippet request is fail", e);
                latch.countDown();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "onResponse: Snippet response is successful");
                stringReference.set(response.body().string());
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stringReference.get();
    }

    @Override
    public void getSnippetAsJsonAsync(Consumer<? super String> stringConsumer) {
        Request request = new Request.Builder().url(AppConstant.SERVER_SNIPPET_URI).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: Snippet request is fail", e);
                stringConsumer.accept(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "onResponse: Snippet response deserialized successfully");
                stringConsumer.accept(response.body().string());
            }
        });
    }

}
