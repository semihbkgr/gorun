package com.semihbkgr.gorun.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;

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
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        titleTextView.setText(snippetInfo.title);
        return convertView;
    }

}
