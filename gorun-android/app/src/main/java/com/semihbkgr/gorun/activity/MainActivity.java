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
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.util.ExpirationTimer;

public class MainActivity extends AppCompatActivity {

    private final ExpirationTimer backTimer = new ExpirationTimer(AppConstants.Values.BACK_BUTTON_TIME_MS, "MenuActivityBackTimer");

    private LinearLayout runLayout;
    private LinearLayout snippetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runLayout = findViewById(R.id.runLayout);
        snippetLayout = findViewById(R.id.snippetLayout);

        runLayout.setOnClickListener(this::onRunLayoutClicked);
        snippetLayout.setOnClickListener(this::onSnippetLayoutClicked);

    }

    private void onRunLayoutClicked(View v) {
        Intent i = new Intent(this, EditorActivity.class);
        startActivity(i);
    }

    private void onSnippetLayoutClicked(View v) {
        Intent i = new Intent(this, SnippetListActivity.class);
        startActivity(i);
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

}