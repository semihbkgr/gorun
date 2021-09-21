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
import com.semihbg.gorun.tutorial.Subject;

import java.util.List;

public class SubjectArrayAdapter extends ArrayAdapter<Subject> {

    public SubjectArrayAdapter(@NonNull Context context, @NonNull List<Subject> objects) {
        super(context, 0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_subject_list_view, parent, false);
        Subject subject=getItem(position);
        TextView textViewTitle=convertView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(subject.getTitle());
        return convertView;
    }

}
