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
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.code.Code;
import com.semihbkgr.gorun.code.CodeService;

import java.util.List;
import java.util.concurrent.Executor;

public class CodeSaveDialog extends AbstractAppDialog {

    private static final String TAG = CodeSaveDialog.class.getName();

    private final CodeService codeService;
    private final Executor executor;

    private final EditText titleEditText;
    private final Button saveButton;

    private Toast toast;

    public CodeSaveDialog(@NonNull Context ctx, @StyleRes int themeResId, @NonNull CodeService codeService, @NonNull Executor executor) {
        super(ctx, themeResId);
        this.codeService = codeService;
        this.executor = executor;
        // customize dialog
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        dialog.setContentView(R.layout.dialog_code_save);
        dialog.setCancelable(true);
        // find views
        this.titleEditText = dialog.findViewById(R.id.titleEditText);
        this.saveButton = dialog.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(this::onSaveButtonClicked);

        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);

    }

    @Override
    public void onShow() {
        Log.i(TAG, "onShow: ");
    }

    @Override
    public void onCancelled() {
        Log.i(TAG, "onHide: ");
    }

    private void onSaveButtonClicked(View v) {
        String title = titleEditText.getText().toString().trim();
        Log.i(TAG, "onSaveButtonClicked: code title: " + title);
        if (title.isEmpty()) {
            Log.w(TAG, "onSaveButtonClicked: code title cannot be empty");
            try {
                if (!toast.getView().isShown()) {
                    toast.setText(R.string.code_title_text);
                    toast.show();
                }
            } catch (Exception e) {
                this.toast = Toast.makeText(getContext(), R.string.code_title_text, Toast.LENGTH_SHORT);
                toast.show();
            }
            return;
        }
        if (!hasProperty(AppConstants.Values.DIALOG_PROPERTY_CODE_CONTENT)) {
            try {
                if (!toast.getView().isShown()) {
                    toast.setText(R.string.code_save_text);
                    toast.show();
                }
            } catch (Exception e) {
                this.toast = Toast.makeText(getContext(), R.string.code_save_text, Toast.LENGTH_SHORT);
                toast.show();
            }
            return;
        }
        String codeContent = getProperty(AppConstants.Values.DIALOG_PROPERTY_CODE_CONTENT, String.class);
        if (codeContent.isEmpty()) {
            Log.w(TAG, "onSaveButtonClicked: code content cannot be empty");
            try {
                if (!toast.getView().isShown()) {
                    toast.setText(R.string.code_content_text);
                    toast.show();
                }
            } catch (Exception e) {
                this.toast = Toast.makeText(getContext(), R.string.code_content_text, Toast.LENGTH_SHORT);
                toast.show();
            }
            return;
        }
        cancel();
        executor.execute(() -> {
            Code code = new Code();
            code.setTitle(title);
            code.setContent(codeContent);
            code.setCreatedAt(System.currentTimeMillis());
            code.setUpdatedAt(0);
            codeService.save(code);
            Log.i(TAG, "onSaveButtonClicked: code is saving, title: " + code.getTitle() + ", createdAt: " + code.getCreatedAt());
            Code savedCode = codeService.save(code);
            if (savedCode.getId() == -1) {
                Log.i(TAG, "onSaveButtonClicked: code cannot be saved");
                v.post(() -> Toast.makeText(getContext(), String.format("Code '%s' saved successfully", code.getTitle()), Toast.LENGTH_SHORT).show());
            } else {
                Log.i(TAG, "onSaveButtonClicked: code saved successfully, id: " + savedCode.getId());
                v.post(() -> Toast.makeText(getContext(), String.format("Code '%s' cannot be saved", code.getTitle()), Toast.LENGTH_SHORT).show());
            }
        });
    }

}
