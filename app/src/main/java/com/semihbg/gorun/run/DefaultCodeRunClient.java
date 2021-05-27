package com.semihbg.gorun.run;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static com.semihbg.gorun.run.CodeRunUtils.error;
import static com.semihbg.gorun.run.CodeRunUtils.sneakyThrow;

public class DefaultCodeRunClient implements CodeRunClient {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void run(String code, Consumer<InputStream> inputStreamConsumer) {
        new OkHttpClient.Builder().setConnectTimeout$okhttp();
        Request request = new Request.Builder()
                .url(RUN_API_URI)
                .post(RequestBody.create(code.getBytes(StandardCharsets.UTF_8)))
                .addHeader("Content-Type","text/plain")
                .addHeader("Content-Length",String.valueOf(code.length()))
                .addHeader("Connection","keep-alive")
                .addHeader("Accept","*/*")
                .addHeader("Accept-Encoding","gzip, deflate, br")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println(System.currentTimeMillis());
                inputStreamConsumer.accept(response.body().byteStream());
            }
        });
    }

}
