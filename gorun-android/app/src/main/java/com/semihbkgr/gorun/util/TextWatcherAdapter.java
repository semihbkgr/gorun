package com.semihbkgr.gorun.util;

import android.text.Editable;
import android.text.TextWatcher;

@FunctionalInterface
public interface TextWatcherAdapter extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    void onTextChanged(CharSequence s, int start, int before, int count);

    @Override
    default void afterTextChanged(Editable s) {
    }

}
