package com.semihbkgr.gorun.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import androidx.annotation.NonNull;
import com.semihbkgr.gorun.R;

public class CodeSaveDialog extends AbstractAppDialog{

    private static final String TAG =CodeSaveDialog.class.getName();

    public CodeSaveDialog(@NonNull Context ctx, int themeResId) {
        super(ctx, themeResId);
        if(dialog.getWindow()!=null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        dialog.setContentView(R.layout.dialog_save_code);
        dialog.setCancelable(true);
    }

    @Override
    public void onShow() {
        Log.i(TAG, "onShow: ");
    }

    @Override
    public void onHide() {
        Log.i(TAG, "onHide: ");
    }

}
