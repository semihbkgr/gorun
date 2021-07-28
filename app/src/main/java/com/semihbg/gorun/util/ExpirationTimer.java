package com.semihbg.gorun.util;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class ExpirationTimer {

    private static final String TAG = ExpirationTimer.class.getName();

    private final Thread thread;
    private final long timeInterval;
    //State
    //-1: Stopped
    //0: Waiting
    //1: OnTime
    private final AtomicInteger state;

    public ExpirationTimer(long timeInterval) {
        this(timeInterval, "ExpirationTimerThread");
    }

    public ExpirationTimer(long timeInterval, String name) {
        if (timeInterval < 1) throw new IllegalArgumentException("TimeInterval argument must be positive value");
        this.timeInterval = timeInterval;
        this.state = new AtomicInteger(0);
        this.thread = new Thread(() -> {
            while (!isStopped()) {
                if (!isWaiting()) {
                    try {
                        Thread.sleep(timeInterval);
                        makeWaiting();
                    } catch (InterruptedException ignore) {
                    }
                } else Thread.yield();
            }
        }, name);
        thread.start();
    }

    public void reflesh() {
        if (isStopped())
            throw new IllegalStateException(this.getClass().getName() + " " + this.thread.getName() + " has already been stopped");
        makeOnTime();
        thread.interrupt();
    }

    public boolean result() {
        if (isStopped())
            throw new IllegalStateException(this.getClass().getName() + " " + this.thread.getName() + " has already been stopped");
        return isOnTime();
    }

    public void stop() {
        if (isStopped())
            throw new IllegalStateException(this.getClass().getName() + " " + this.thread.getName() + " has already been stopped");
        makeStopped();
        thread.interrupt();
        Log.i(TAG, this.getClass().getName() + " " + this.thread.getName() + " has been stopped successfully");
    }

    private boolean isStopped() {
        return state.get() == -1;
    }

    private void makeStopped() {
        state.set(-1);
    }

    private boolean isWaiting() {
        return state.get() == 0;
    }

    private void makeWaiting() {
        state.set(0);
    }

    private boolean isOnTime() {
        return state.get() == 1;
    }

    private void makeOnTime() {
        state.set(1);
    }


}
