package com.semihbkgr.gorun.dialog;

import android.content.Context;

public interface AppDialog {

    Context getContext();

    void show();

    void cancel();

    void addProperty(String key, Object data);

    void removeProperty(String key);

    boolean hasProperty(String key);

    Object getProperty(String key);

    <T> T getProperty(String key, Class<T> cls) throws ClassCastException;

    void onShow();

    void onCancelled();

}
