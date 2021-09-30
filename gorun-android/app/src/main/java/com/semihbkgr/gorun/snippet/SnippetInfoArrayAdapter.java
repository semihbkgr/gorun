package com.semihbkgr.gorun.snippet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.List;

public class SnippetInfoArrayAdapter extends ArrayAdapter<SnippetInfo> {

    public SnippetInfoArrayAdapter(@NonNull Context context, @NonNull List<SnippetInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_snippet_list_view, parent, false);
        SnippetInfo snippetInfo = getItem(position);
        if (snippetInfo == null)
            return convertView;
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        titleTextView.setText(snippetInfo.title);
        TextView briefTextView = convertView.findViewById(R.id.briefTextView);
        briefTextView.setText(snippetInfo.brief);
        ImageButton saveButton = convertView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            AppContext.instance().snippetService.getSnippetAsync(snippetInfo.id, new ResponseCallback<Snippet>() {
                @Override
                public void onResponse(Snippet data) {
                    AppContext.instance().snippetService.save(data);
                    System.out.println(data);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        });
        return convertView;
    }

}
