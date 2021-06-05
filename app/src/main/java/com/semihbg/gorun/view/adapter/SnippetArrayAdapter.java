package com.semihbg.gorun.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.semihbg.gorun.R;
import com.semihbg.gorun.snippet.Snippet;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnippetArrayAdapter extends ArrayAdapter<Snippet> {

    public SnippetArrayAdapter(@NonNull @NotNull Context context, @NonNull @NotNull List<Snippet> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_snippet_list_view, parent, false);
        Snippet snippet=getItem(position);
        TextView titleTextView=convertView.findViewById(R.id.titleTextView);
        titleTextView.setText(snippet.title);
        return convertView;
    }

}
