package com.semihbkgr.gorun.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import java.util.HashMap;

public abstract class AbstractAppDialog implements AppDialog {

    protected final Dialog dialog;
    private final HashMap<String, Object> propertyMap;

    protected AbstractAppDialog(@NonNull Context ctx, @StyleRes int themeResId) {
        this.dialog = new Dialog(ctx, themeResId);
        dialog.setOnCancelListener(dialog -> {
            onCancelled();
        });
        this.propertyMap = new HashMap<>();
    }

    @Override
    public Context getContext() {
        return dialog.getContext();
    }

    @Override
    public final void show() {
        dialog.show();
        onShow();
    }

    @Override
    public final void cancel() {
        dialog.hide();
    }

    @Override
    public final void addProperty(@NonNull String key, @NonNull Object data) {
        propertyMap.put(key, data);
    }

    @Override
    public void removeProperty(@NonNull String key) {
        propertyMap.remove(key);
    }

    @Override
    public boolean hasProperty(@NonNull String key) {
        return propertyMap.containsKey(key);
    }

    @Nullable
    @Override
    public Object getProperty(@NonNull String key) {
        return this.propertyMap.get(key);
    }

    @Override
    public <T> T getProperty(@NonNull String key, @NonNull Class<T> cls) throws ClassCastException {
        return cls.cast(propertyMap.get(key));
    }

}
