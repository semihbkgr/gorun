package com.semihbkgr.gorun.snippet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.semihbkgr.gorun.util.http.RequestException;
import com.semihbkgr.gorun.util.http.ResponseCallback;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("GetSnippetId1")
    void getSnippetId1() {
        try {
            Snippet snippet = snippetClient.getSnippet(1);
            assertNotNull(snippet);
            LOG.info(snippet.toString());
        } catch (RequestException e) {
            fail(e);
        }
    }

    @Test
    @DisplayName("GetSnippetAsyncId1")
    void getSnippetAsyncId1() throws InterruptedException {
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
    @DisplayName("GetSnippetFutureId1")
    void getSnippetFutureId1() throws ExecutionException, InterruptedException {
        Future<Snippet> snippetFuture = snippetClient.getSnippetFuture(1);
        Snippet snippet = snippetFuture.get();
        assertNotNull(snippet);
        LOG.info(snippet.toString());
    }


    @Test
    @DisplayName("GetSnippetId0")
    void getSnippetId0() {
        try {
            Snippet snippet = snippetClient.getSnippet(0);
        } catch (RequestException e) {
            assertTrue(e.errorResponseModelOptional().isPresent());
        }
    }

    @Test
    @DisplayName("GetSnippetAsyncId0")
    void getSnippetAsyncId0() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        snippetClient.getSnippetAsync(0, new ResponseCallback<Snippet>() {
            @Override
            public void onResponse(Snippet snippet) {
                fail();
            }

            @Override
            public void onFailure(Exception e) {
                if(e instanceof RequestException){
                    assertTrue(((RequestException)e).errorResponseModelOptional().isPresent());
                    latch.countDown();
                }
                else
                    fail();

            }
        });
        latch.await();
    }

    @Test
    @DisplayName("GetSnippetFutureId0")
    void getSnippetFutureId0() throws ExecutionException, InterruptedException {
        Future<Snippet> snippetFuture = snippetClient.getSnippetFuture(0);
        Snippet snippet = snippetFuture.get();
        assertNotNull(snippet);
        LOG.info(snippet.toString());
    }


}