package com.semihbg.gorun.activity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.tutorial.Subject;
import com.semihbg.gorun.view.adapter.SubjectArrayAdapter;

import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    private ListView listViewSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        listViewSubject = findViewById(R.id.listViewSubject);
        List<Subject> subjectList = AppContext.instance().tutorialService.findSubjects();
        ListAdapter listAdapter = new SubjectArrayAdapter(this, subjectList);
        listViewSubject.setAdapter(listAdapter);

    }


}