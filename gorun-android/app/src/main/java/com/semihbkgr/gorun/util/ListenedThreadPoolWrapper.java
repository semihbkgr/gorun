package com.semihbkgr.gorun.util;

import androidx.annotation.NonNull;

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

    public void listenedExecute(@NonNull Runnable runnable, @NonNull Consumer<Void> callback) {
        threadPoolExecutor.execute(() -> {
            runnable.run();
            callback.accept(null);
        });
    }

    public <T> void listenedExecute(@NonNull Callable<T> callable, @NonNull Consumer<? super T> callback) {
        threadPoolExecutor.execute(() -> {
            try {
                T data = callable.call();
                callback.accept(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void execute(@NonNull Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }

    public Future<?> submit(@NonNull Runnable task) {
        return threadPoolExecutor.submit(task);
    }

    public <T> Future<T> submit(@NonNull Callable<T> task) {
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
