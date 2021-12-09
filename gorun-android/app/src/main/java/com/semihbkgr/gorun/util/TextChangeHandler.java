package com.semihbkgr.gorun.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class TextChangeHandler implements Runnable {

    private static final String TAG = TextChangeHandler.class.getName();

    private final long delay;
    private final StringBuffer appendText;
    private final Thread thread;
    private final List<TextChangeListener> textChangeListenerList;
    private final AtomicBoolean isRunning;
    private final AtomicBoolean forceUpdate;
    private final AtomicBoolean isUpdate;
    private final AtomicLong textLastUpdateTimeMs;
    private final StringBuilder fullText;

    public TextChangeHandler(long delay) {
        if (delay < 0)
            throw new IllegalArgumentException("Delay must be positive value");
        this.delay = delay;
        this.textChangeListenerList = new ArrayList<>();
        this.appendText = new StringBuffer();
        this.fullText = new StringBuilder();
        this.isRunning = new AtomicBoolean(true);
        this.forceUpdate = new AtomicBoolean(false);
        this.isUpdate = new AtomicBoolean(false);
        this.textLastUpdateTimeMs = new AtomicLong(-1);
        this.thread = new Thread(this);
        thread.setName("TextHandlerThread");
        thread.setDaemon(true);
        thread.start();
    }

    public void addListener(TextChangeListener textChangeListener) {
        if (textChangeListener != null) this.textChangeListenerList.add(textChangeListener);
        else Log.w(TAG, "addListener: TextChangeListener cannot be null");
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void append(String text) {
        this.append(text, false);
    }

    public void append(String text, boolean updateForce) {
        this.appendText.append(text);
        //Avoid update atomic reference too much
        if (updateForce) this.forceUpdate.set(true);
        else updateTextLastUpdateTimeNow();
    }

    public void clear() {
        this.fullText.delete(0, appendText.length());
        this.isUpdate.set(true);
        this.forceUpdate.set(true);
    }

    public void set(String text) {
        this.fullText.delete(0, appendText.length());
        this.append(text, true);
        this.isUpdate.set(true);
        this.forceUpdate.set(true);
    }

    public String getFullText() {
        return fullText.toString();
    }

    public String getAppendText() {
        return appendText.toString();
    }

    private void updateTextLastUpdateTimeNow() {
        this.textLastUpdateTimeMs.set(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            if (forceUpdate.get()) {
                change();
                forceUpdate.set(false);
            } else if (textLastUpdateTimeMs.get() > 0 &&
                    System.currentTimeMillis() - textLastUpdateTimeMs.get() >= delay) {
                change();
            }
        }
    }

    private void change() {
        Log.i(TAG, "change: Update");
        if (isUpdate.get()) {
            isUpdate.set(false);
            for (TextChangeListener textChangeListener : textChangeListenerList)
                textChangeListener.onChanged(TextChangeListener.Event.UPDATE, fullText.toString());
        } else {
            for (TextChangeListener textChangeListener : textChangeListenerList)
                textChangeListener.onChanged(TextChangeListener.Event.APPEND, appendText.toString());
            fullText.append(appendText);
            this.appendText.delete(0, appendText.length());
            this.textLastUpdateTimeMs.set(-1);
        }
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

}
