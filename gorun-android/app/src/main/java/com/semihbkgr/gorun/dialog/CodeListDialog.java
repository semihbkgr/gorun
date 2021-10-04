package com.semihbkgr.gorun.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.code.Code;
import com.semihbkgr.gorun.code.CodeInfo;
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
        codeListView.setAdapter(null);
        infoTextView.setText(R.string.loading);
        executor.execute(()->{
            List<CodeInfo> codeInfoList=codeService.getAllInfos();
            ArrayAdapter<CodeInfo> arrayAdapter=new CodeSaveListViewAdapter(getContext(),codeInfoList,codeService,executor);
            codeListView.post(()->{
                codeListView.setAdapter(arrayAdapter);
                infoTextView.setText("Code count: "+codeInfoList.size());
            });
        });
    }

    @Override
    public void onCancelled() {
        Log.i(TAG, "onCancelled");
    }


    private static class CodeSaveListViewAdapter extends ArrayAdapter<CodeInfo> {

        private final CodeService codeService;
        private final Executor executor;

        private CodeSaveListViewAdapter(@NonNull Context context, @NonNull List<CodeInfo> codeInfos, @NonNull CodeService codeService, @NonNull Executor executor) {
            super(context, 0, codeInfos);
            this.codeService = codeService;
            this.executor = executor;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_code_list_view, parent, false);
            CodeInfo codeInfos = getItem(position);
            TextView titleTextView = convertView.findViewById(R.id.titleTextView);
            titleTextView.setText(codeInfos.getTitle());
            TextView createdAtTextView = convertView.findViewById(R.id.createdAtTextView);
            createdAtTextView.setText("CreatedAt: " + codeInfos.getCreatedAt());
            TextView updatedAtTextView = convertView.findViewById(R.id.updatedAtTextView);
            updatedAtTextView.setText("UpdatedAt: " + codeInfos.getUpdatedAt());
            ImageButton deleteImageButton = convertView.findViewById(R.id.deleteImageButton);
            deleteImageButton.setOnClickListener(v -> {
                Log.i(TAG, "getView: deleteImageButton clicked");
                remove(codeInfos);
                executor.execute(() -> {
                    int count = codeService.delete(codeInfos.getId());
                    if (count > 0) {
                        Log.i(TAG, "getView: code deleted successfully, id: " + codeInfos.getId());
                        deleteImageButton.post(() -> Toast.makeText(getContext(), String.format("Code '%s' deleted successfully", codeInfos.getTitle()), Toast.LENGTH_SHORT).show());
                    } else {
                        Log.w(TAG, "getView: code cannot be deleted, id: " + codeInfos.getId());
                        deleteImageButton.post(() -> {
                            Toast.makeText(getContext(), String.format("Code '%s' cannot be deleted", codeInfos.getTitle()), Toast.LENGTH_SHORT).show();
                            add(codeInfos);
                        });
                    }
                });
            });
            return convertView;
        }

    }

}
