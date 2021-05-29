package com.semihbg.gorun.server;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerApplicationTest {

    @Test
    void testWebSocket() throws InterruptedException {

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder().url("ws://localhost:8080/echo").build();
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("onClose");
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
                    while(true){
                        webSocket.send("run:package main\n" +
                                "import \"fmt\"\n" +
                                "func main() {\n" +
                                "    fmt.Println(\"hello world\")\n" +
                                "}");
                        System.out.println("Send Hello");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        Thread.sleep(10000000);

    }



}