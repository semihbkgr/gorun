package com.semihbkgr.gorun.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

public abstract class AbstractAppDialog implements AppDialog {

    protected final Dialog dialog;

    protected AbstractAppDialog(@NonNull Context ctx, @StyleRes int themeResId) {
        this.dialog=new Dialog(ctx,themeResId);
    }

    @Override
    public void show() {
        dialog.show();
        onShow();
    }

    @Override
    public void hide() {
        dialog.hide();
        onHide();
    }

}
