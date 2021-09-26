package com.semihbkgr.gorun.snippet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.util.RequestException;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnippetClientImplTest {

    SnippetClientImpl snippetClient;

    @BeforeEach
    void initializeRequestObjects() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder().create();
        this.snippetClient = new SnippetClientImpl(httpClient, gson);
    }

    @Test
    @DisplayName("GetAllSnippetInfos")
    void getAllSnippetInfos(){
        try {
            SnippetInfo[] snippetInfos=snippetClient.getAllSnippetInfos();
        } catch (RequestException e) {
            fail(e);
        }
    }

}