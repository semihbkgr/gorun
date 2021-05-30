package com.semihbg.gorun.server;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class CodeRunIntegrationTest {

    @Test
    void testWebSocket() throws InterruptedException {

        OkHttpClient client=new OkHttpClient();
        CountDownLatch closeCDL=new CountDownLatch(1);

        Request request=new Request.Builder().url("ws://localhost:8080/run").build();
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("onClose");
                closeCDL.countDown();
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("onClosing");
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                System.out.println("onFailure");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println("onMessage");
                System.out.println(String.format("ResponseMessage: %s", text));
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                System.out.println("onMessage");
                System.out.println(String.format("ResponseMessage: %s", bytes));
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("onOpen");
                new Thread(()->{
                    webSocket.send("[run:package main\n" +
                            "\n" +
                            "import (\n" +
                            "    \"fmt\"\n" +
                            "    \"time\"\n" +
                            ")\n" +
                            "\n" +
                            "func main() {\n" +
                            "    fmt.Printf(\"Current Unix Time: %v\\n\", time.Now().Unix())\n" +
                            "\n" +
                            "    time.Sleep(20 * time.Second)\n" +
                            "\n" +
                            "    fmt.Printf(\"Current Unix Time: %v\\n\", time.Now().Unix())\n" +
                            "}]");
                }).start();
            }
        });

        closeCDL.await();

    }



}