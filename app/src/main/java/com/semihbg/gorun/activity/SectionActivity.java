package com.semihbg.gorun.activity;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppConstants;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.tutorial.Section;

public class SectionActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        //Get section by title
        String title=getIntent().getStringExtra(AppConstants.INTENT_EXTRA_SECTION_TITLE);
        Section section= AppContext.instance().tutorialService.getSection(title);

        //Find Views
        titleTextView =findViewById(R.id.titleTextView);
        contentTextView =findViewById(R.id.contentTextView);

        titleTextView.setText(section.title);
        contentTextView.setText(section.content);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
