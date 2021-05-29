package com.semihbg.gorun.util;

import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class TextChangeUpdater implements Runnable{

    private static final String TAG=TextChangeUpdater.class.getName();

    private final StringBuilder text;
    private final AtomicBoolean change;
    private volatile boolean stop;
    private final TextView textView;

    public TextChangeUpdater(TextView textView) {
        this.textView=textView;
        text=new StringBuilder();
        change=new AtomicBoolean(false);
        stop=false;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void append(String str){
        text.append(str);
        change.set(true);
        Log.i(TAG, "append: Text has been appended successfully");
    }

    public void clear(){
        text.delete(0,text.length());
        change.set(true);
        Log.i(TAG, "clear: Text has been cleared successfully");
    }

    @Override
    public void run() {
        while(!stop){
            if(change.get()){
                change.set(false);
                textView.post(()-> textView.setText(text.toString()));
                Log.i(TAG, "stop: Text changes has been reflected to TextView successfully");
            }else Thread.yield();
        }
    }

    public void stop(){
        stop=true;
        Log.i(TAG, "stop: TextChangeUpdater has been stopped successfully");
    }

}
