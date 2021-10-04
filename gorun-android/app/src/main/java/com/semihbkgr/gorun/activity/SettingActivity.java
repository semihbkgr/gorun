package com.semihbkgr.gorun.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbkgr.gorun.R;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG=SettingActivity.class.getName();

    private TextView serverStateValueTextView;
    private Button deleteSubjectsButton;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Setting");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        serverStateValueTextView=findViewById(R.id.serverStateValueTextView);
        deleteSubjectsButton =findViewById(R.id.buttonDeleteSubjects);

        deleteSubjectsButton.setOnClickListener(this::onButtonClearSubjectClicked);


    }


    private void onButtonClearSubjectClicked(View v){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingItem) {
            Intent intent = new Intent(this, SettingActivity.class);
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