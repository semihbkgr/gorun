package com.semihbkgr.gorun.snippet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.util.RequestException;
import com.semihbkgr.gorun.util.ResponseCallback;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class SnippetClientImplTest {

    final static Logger LOG = Logger.getLogger(SnippetClientImplTest.class.getName());

    SnippetClientImpl snippetClient;

    @BeforeEach
    void initializeRequestObjects() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder().create();
        this.snippetClient = new SnippetClientImpl(httpClient, gson);
    }

    @Test
    @DisplayName("GetAllSnippetInfos")
    void getAllSnippetInfos() {
        try {
            SnippetInfo[] snippetInfos = snippetClient.getAllSnippetInfos();
            assertNotNull(snippetInfos);
            LOG.info("Snippet count: " + snippetInfos.length);
            Stream.of(snippetInfos).map(SnippetInfo::toString).forEach(LOG::info);
        } catch (RequestException e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("GetAllSnippetInfosAsync")
    void getAllSnippetInfosAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        snippetClient.getAllSnippetInfosAsync(new ResponseCallback<SnippetInfo[]>() {
            @Override
            public void onResponse(SnippetInfo[] snippetInfos) {
                assertNotNull(snippetInfos);
                LOG.info("Snippet count: " + snippetInfos.length);
                Stream.of(snippetInfos).map(SnippetInfo::toString).forEach(LOG::info);
                latch.countDown();
            }

            @Override
            public void onFailure(Exception e) {
                fail(e);
            }
        });
        latch.await();
    }

    @Test
    @DisplayName("GetAllSnippetInfosFuture")
    void getAllSnippetInfosFuture() throws ExecutionException, InterruptedException {
        Future<SnippetInfo[]> snippetInfosFuture = snippetClient.getAllSnippetInfoFuture();
        SnippetInfo[] snippetInfos = snippetInfosFuture.get();
        LOG.info("Snippet count: " + snippetInfos.length);
        Stream.of(snippetInfos).map(SnippetInfo::toString).forEach(LOG::info);
    }

    @Test
    @DisplayName("GetSnippet")
    void getSnippet() {
        try {
            Snippet snippet = snippetClient.getSnippet(1);
            assertNotNull(snippet);
            LOG.info(snippet.toString());
        } catch (RequestException e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("GetSnippetAsync")
    void getSnippetAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        snippetClient.getSnippetAsync(1, new ResponseCallback<Snippet>() {
            @Override
            public void onResponse(Snippet snippet) {
                assertNotNull(snippet);
                LOG.info(snippet.toString());
                latch.countDown();
            }

            @Override
            public void onFailure(Exception e) {
                fail(e);
            }
        });
        latch.await();
    }

    @Test
    @DisplayName("GetSnippetFuture")
    void getSnippetFuture() throws ExecutionException, InterruptedException {
        Future<Snippet> snippetFuture= snippetClient.getSnippetFuture(1);
        Snippet snippet=snippetFuture.get();
        assertNotNull(snippet);
        LOG.info(snippet.toString());
    }


}