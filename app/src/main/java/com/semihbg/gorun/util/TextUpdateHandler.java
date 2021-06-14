package com.semihbg.gorun.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class TextUpdateHandler implements Runnable {

    private static final String TAG = TextUpdateHandler.class.getName();

    private final long delay;
    private final StringBuffer stringBuffer;
    private final List<Consumer<? super String>> consumerListenerList;
    private final Thread thread;
    private final AtomicBoolean isRunning;
    private AtomicLong textLastUpdateTimeMs;
    private StringBuilder text;

    public TextUpdateHandler(long delay) {
        this.delay = delay;
        stringBuffer = new StringBuffer();
        consumerListenerList = new ArrayList<>();
        this.isRunning = new AtomicBoolean(true);
        this.textLastUpdateTimeMs = new AtomicLong(-1);
        this.thread = new Thread(this);
        thread.setName("TextHandlerThread");
        thread.setDaemon(true);
        thread.start();
    }


    public void addListener(Consumer<? super String> consumerListener) {
        if (consumerListener != null)
            consumerListenerList.add(consumerListener);
        else
            Log.w(TAG, "addListener: consumerListener parameter cannot be null");
    }

    public Thread getThread() {
        return this.thread;
    }

    public void stop() {
        if (isRunning.get()) {
            isRunning.set(false);
            Log.i(TAG, "stop: Thread has been stopped");
        } else
            Log.w(TAG, "stop: Thread is already stopped");
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void append(String text) {
        this.stringBuffer.append(text);
        updateTextLastUpdateTimeNow();
    }

    public void clear() {
        this.stringBuffer.delete(0, stringBuffer.length());
        updateTextLastUpdateTimeNow();
    }

    public void set(String text) {
        this.stringBuffer.delete(0, stringBuffer.length());
        this.stringBuffer.append(text);
        updateTextLastUpdateTimeNow();
    }

    public String getText() {
        return text.toString();
    }

    private void updateTextLastUpdateTimeNow() {
        this.textLastUpdateTimeMs.set(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            if (textLastUpdateTimeMs.getAndIncrement() > 0) {
                if (System.currentTimeMillis() - textLastUpdateTimeMs.get() >= delay) {
                    textLastUpdateTimeMs.set(-1);
                    for (Consumer<? super String> consumer : consumerListenerList)
                        consumer.accept(stringBuffer.toString());
                    text.append(stringBuffer.toString());
                    this.stringBuffer.delete(0, stringBuffer.length());
                }
            }
        }
    }

}
