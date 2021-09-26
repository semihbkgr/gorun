package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.AppConstant;
import com.semihbkgr.gorun.util.ExpirationTimer;

public class MenuActivity extends AppCompatActivity {

    private final ExpirationTimer backTimer = new ExpirationTimer(AppConstant.BACK_BUTTON_TIME_MS, "MenuActivityBackTimer");

    private LinearLayout editorLayout;
    private LinearLayout snippetLayout;
    private LinearLayout docLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // find views
        editorLayout = findViewById(R.id.editorLayout);
        snippetLayout = findViewById(R.id.snippetLayout);
        docLayout=findViewById(R.id.docLayout);

        editorLayout.setOnClickListener(this::onEditorLayoutClicked);
        snippetLayout.setOnClickListener(this::onSnippetLayoutClicked);
        docLayout.setOnClickListener(this::onDocLayoutClicked);

    }

    private void onEditorLayoutClicked(View v) {
        Intent i=new Intent(this,EditorActivity.class);
        startActivity(i);
    }

    private void onSnippetLayoutClicked(View v) {

    }

    private void onDocLayoutClicked(View v){

    }

    @Override
    public void onBackPressed() {
        if (backTimer.result())
            super.onBackPressed();
        else backTimer.reflesh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
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

}