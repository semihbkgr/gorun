package com.semihbkgr.gorun.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.code.CodeService;

import java.util.List;
import java.util.concurrent.Executor;

public class CodeListDialog extends AbstractAppDialog{

    private static final String TAG=CodeListDialog.class.getName();

    private final CodeService codeService;
    private final Executor executor;

    private final ListView codeListView;
    private final TextView infoTextView;

    public CodeListDialog(@NonNull Context ctx, @StyleRes int themeResId, @NonNull CodeService codeService, @NonNull Executor executor) {
        super(ctx, themeResId);
        this.codeService=codeService;
        this.executor=executor;
        // customize dialog
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        dialog.setContentView(R.layout.dialog_code_list);
        dialog.setCancelable(true);
        // find views
        this.codeListView =dialog.findViewById(R.id.codeListView);
        this.infoTextView=dialog.findViewById(R.id.infoTextView);

    }

    @Override
    public void onShow() {
        Log.i(TAG, "onShow");
        codeListView.removeAllViews();
        infoTextView.setText(R.string.loading);
        executor.execute(()->{
            List<String> codeTitleList=codeService.findAllTitle();

        });
    }

    @Override
    public void onCancelled() {
        Log.i(TAG, "onCancelled");
    }

}
