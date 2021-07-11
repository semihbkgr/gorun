package com.semihbg.gorun.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public class ListenedThreadPoolWrapper {

    private final ThreadPoolExecutor threadPoolExecutor;

    public ListenedThreadPoolWrapper(int threadCount) {
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    public void listenedExecute(Runnable runnable, Consumer<Void> callback) {
        threadPoolExecutor.execute(() -> {
            runnable.run();
            callback.accept(null);
        });
    }

    public <T> void listenedExecute(Callable<T> callable, Consumer<? super T> callback) {
        threadPoolExecutor.execute(() -> {
            try {
                T data = callable.call();
                callback.accept(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Future<?> submit(Runnable task) {
        return threadPoolExecutor.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return threadPoolExecutor.shutdownNow();
    }

    public boolean isShutdown() {
        return threadPoolExecutor.isShutdown();
    }

    public boolean isTerminating() {
        return threadPoolExecutor.isTerminating();
    }

    public boolean isTerminated() {
        return threadPoolExecutor.isTerminated();
    }

}
