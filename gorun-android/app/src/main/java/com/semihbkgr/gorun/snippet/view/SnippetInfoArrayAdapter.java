package com.semihbkgr.gorun.snippet.view;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.List;

public class SnippetInfoArrayAdapter extends ArrayAdapter<SnippetInfoViewModelHolder> {

    private static final String TAG = SnippetInfoArrayAdapter.class.getName();

    public SnippetInfoArrayAdapter(@NonNull Context context, @NonNull List<SnippetInfoViewModelHolder> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_snippet_list_view, parent, false);
        SnippetInfoViewModelHolder snippetInfoViewModelHolder = getItem(position);
        SnippetInfo snippetInfo = snippetInfoViewModelHolder.snippetInfo;
        if (snippetInfo == null)
            return convertView;
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        titleTextView.setText(snippetInfo.title);
        TextView briefTextView = convertView.findViewById(R.id.briefTextView);
        briefTextView.setText(snippetInfo.brief);
        ImageButton saveButton = convertView.findViewById(R.id.saveButton);
        saveButton.setImageDrawable(getContext().getDrawable(snippetInfoViewModelHolder.isDownloaded() ? R.drawable.delete : R.drawable.download));
        saveButton.setOnClickListener(v -> {
            saveButton.setClickable(false);
            saveButton.setImageDrawable(getContext().getDrawable(R.drawable.waiting));
            if (snippetInfoViewModelHolder.isDownloaded()) {
                AppContext.instance().executorService.execute(() -> {
                    try {
                        AppContext.instance().snippetService.delete(snippetInfo.id);
                        Log.i(TAG, "getView: snippet has been deleted, snippetId: " + snippetInfo.id);
                        snippetInfoViewModelHolder.setDownloaded(false);
                        new Handler(getContext().getMainLooper()).post(() -> {
                            saveButton.setImageDrawable(getContext().getDrawable(R.drawable.download));
                            Toast.makeText(getContext(), String.format("Snippet '%s' deleted", snippetInfo.title), Toast.LENGTH_SHORT).show();
                            saveButton.setClickable(true);
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "getView: error while deleting snippet, snippetId: " + snippetInfo.id, e);
                        new Handler(getContext().getMainLooper()).post(() -> {
                            saveButton.setImageDrawable(getContext().getDrawable(R.drawable.delete));
                            Toast.makeText(getContext(), "Error while deleting", Toast.LENGTH_SHORT).show();
                            saveButton.setClickable(true);
                        });
                    }
                });
            } else {
                saveButton.setImageDrawable(getContext().getDrawable(R.drawable.waiting));
                AppContext.instance().snippetService.getSnippetAsync(snippetInfo.id, new ResponseCallback<Snippet>() {
                    @Override
                    public void onResponse(Snippet data) {
                        try {
                            AppContext.instance().snippetService.save(data);
                            Log.i(TAG, "getView: snippet has been downloaded, snippetId: " + snippetInfo.id);
                            snippetInfoViewModelHolder.setDownloaded(true);
                            new Handler(getContext().getMainLooper()).post(() -> {
                                saveButton.setImageDrawable(getContext().getDrawable(R.drawable.delete));
                                Toast.makeText(getContext(), String.format("Snippet '%s' downloaded", snippetInfo.title), Toast.LENGTH_SHORT).show();
                                saveButton.setClickable(true);
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "getView: error while downloading snippet, snippetId: " + snippetInfo.id, e);
                            new Handler(getContext().getMainLooper()).post(() -> {
                                saveButton.setImageDrawable(getContext().getDrawable(R.drawable.download));
                                Toast.makeText(getContext(), "Error while downloading", Toast.LENGTH_SHORT).show();
                                saveButton.setClickable(true);
                            });
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: error while downloading snippet, snippetId: " + snippetInfo.id, e);
                        new Handler(getContext().getMainLooper()).post(() -> {
                            saveButton.setImageDrawable(getContext().getDrawable(R.drawable.download));
                            Toast.makeText(getContext(), String.format("Snippet '%s' downloaded", snippetInfo.title), Toast.LENGTH_SHORT).show();
                            saveButton.setClickable(true);
                        });
                    }
                });
            }

        });
        return convertView;
    }

}
