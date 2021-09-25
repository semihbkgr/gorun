package com.semihbkgr.gorun.server;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

class ReactorAndInputStream {

    @Test
    void reactiveFileInputStream() throws FileNotFoundException {
        var dataBufferFlux = DataBufferUtils.readInputStream(
                () -> new FileInputStream("./src/test/resources/text.txt"),
                new DefaultDataBufferFactory(), 256);
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
    }

    @Test
    void reactiveProcessInputStream() throws IOException {
        var dataBufferFlux = DataBufferUtils.readInputStream(
                () -> new ProcessBuilder()
                        .command("java", "./src/test/resources/HelloWorld.java")
                        .start()
                        .getInputStream(),
                new DefaultDataBufferFactory(), 256);
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
    }

    @Test
    void parallelReactiveProcessInputStream() throws InterruptedException {
        var countDownLatch = new CountDownLatch(3);
        var dataBufferFlux = DataBufferUtils.readInputStream(
                () -> new ProcessBuilder()
                        .command("java", "./src/test/resources/Sleep.java")
                        .start()
                        .getInputStream(),
                new DefaultDataBufferFactory(), 256)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnComplete(countDownLatch::countDown);
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        dataBufferFlux.subscribe(df -> {
            System.out.println(df.toString(StandardCharsets.UTF_8));
        });
        countDownLatch.await();
    }

    @Test
    void deferTest() {

        var mono = Mono.defer(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
            return Mono.just("Hello");
        }).subscribeOn(Schedulers.boundedElastic());

        StepVerifier.create(mono).expectNext("Hello").verifyComplete();

    }


}
