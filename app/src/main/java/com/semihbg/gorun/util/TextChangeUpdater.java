package com.semihbg.gorun.util;

import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class TextChangeUpdater implements Runnable{

    private final StringBuilder text;
    private final AtomicBoolean change;
    private volatile boolean stop;
    private final TextView textView;
    private final Thread thread;

    public TextChangeUpdater(TextView textView) {
        this.textView=textView;
        text=new StringBuilder();
        change=new AtomicBoolean(false);
        stop=false;
        thread=new Thread(this);
        thread.start();
    }

    public void append(String str){
        text.append(str);
        change.set(true);
    }

    public void clear(){
        text.delete(0,text.length());
        change.set(true);
    }


    @Override
    public void run() {
        while(!stop){
            if(change.get()){
                change.set(false);
                textView.post(()-> textView.setText(text.toString()));
            }else Thread.yield();
        }
    }

    public void stop(){
        stop=true;
    }

}
