package com.semihbkgr.gorun.server;

import com.semihbkgr.gorun.server.component.DefaultMessageMarshallComponent;
import com.semihbkgr.gorun.server.command.Command;
import com.semihbkgr.gorun.server.command.Message;
import com.semihbkgr.gorun.server.util.InputBuilder;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

class CodeRunIntegrationTest {

    public static void main(String[] args) throws InterruptedException {
        new CodeRunIntegrationTest().runCode();
    }

    @Test
    void runCode() throws InterruptedException {

        OkHttpClient client=new OkHttpClient();
        CountDownLatch closeCDL=new CountDownLatch(1);

        DefaultMessageMarshallComponent messageMarshallComponent=new DefaultMessageMarshallComponent();
        InputBuilder inputBuilder=new InputBuilder(100);
        inputBuilder.addConsumer(Command.OUTPUT,(s ->{
            System.out.println(">>"+s);
        }));
        inputBuilder.addConsumer(Command.ERROR,(s ->{
            System.out.println("--"+s);
        }));

        Request request=new Request.Builder().url("ws://localhost:8080/run").build();
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
                t.printStackTrace();
                closeCDL.countDown();
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println("onMessage");
                inputBuilder.message(messageMarshallComponent.unmarshall(text,true));
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                System.out.println("onMessage");
                inputBuilder.message(messageMarshallComponent.unmarshall(bytes.string(StandardCharsets.UTF_8),true));
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("onOpen");
                new Thread(()->{
                    Scanner scanner=new Scanner(System.in);
                    while(true){
                        try{
                            System.out.print("Command : ");
                            String commandString=scanner.nextLine();
                            Command command=Command.valueOf(commandString);
                            System.out.print("Body : ");
                            String body=scanner.nextLine();
                            webSocket.send(messageMarshallComponent.marshall(Message.of(command,body),false));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        closeCDL.await();

    }



}