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
            if (snippetInfoViewModelHolder.isDownloaded()) {
                snippetInfoViewModelHolder.setDownloaded(false);
                AppContext.instance().executorService.execute(() -> {
                    AppContext.instance().snippetService.delete(snippetInfo.id);
                    new Handler(getContext().getMainLooper()).post(()->saveButton.setImageDrawable(getContext().getDrawable(R.drawable.download)));
                    Log.i(TAG, "getView: snippet has been deleted, snippetId: " + snippetInfo.id);
                });
            } else {
                snippetInfoViewModelHolder.setDownloaded(true);
                AppContext.instance().snippetService.getSnippetAsync(snippetInfo.id, new ResponseCallback<Snippet>() {
                    @Override
                    public void onResponse(Snippet data) {
                        AppContext.instance().snippetService.save(data);
                        new Handler(getContext().getMainLooper()).post(()->saveButton.setImageDrawable(getContext().getDrawable(R.drawable.delete)));
                        Log.i(TAG, "getView: snippet has been downloaded, snippetId: " + snippetInfo.id);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: error while downloading snippet, snippetId: " + snippetInfo.id, e);
                    }
                });
            }

        });
        return convertView;
    }

}
