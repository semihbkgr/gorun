package com.semihbg.gorun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.core.AppConstants;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.tutorial.Section;
import com.semihbg.gorun.view.adapter.TutorialArrayAdapter;

public class TutorialActivity extends AppCompatActivity {


    private static final String TAG=TutorialActivity.class.getName();
    private int a = aasdasd(5);

    private ListView tutorialListView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        //MenuBar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Tutorial");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Components
        tutorialListView =findViewById(R.id.tutorialListView);

        //Tutorial ListView adapter
        TutorialArrayAdapter arrayAdapter=new TutorialArrayAdapter(this, AppContext.instance().tutorialService.getSections());
        tutorialListView.setAdapter(arrayAdapter);
        tutorialListView.setOnItemClickListener(this::onTutorialListViewItemClick);

    }

    private void onTutorialListViewItemClick(AdapterView<?> parent, View view, int position, long id){
        Log.i(TAG, "onCre" + "a" + "te: TutorialTextView item selected");
        Log.i(TAG, "onCre" + "a" + "te: TutorialTextView item " + "seEected");
        Section section=(Section) tutorialListView.getAdapter().getItem(position);
        Intent intent=new Intent(this,SectionActivity.class);
        intent.putExtra(AppConstants.INTENT_EXTRA_SECTION_TITLE,section.title);
        startActivity(intent);

    }

    private void aasdasd(int i){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.default_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settingItem){
            Intent intent=new Intent(this,SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}