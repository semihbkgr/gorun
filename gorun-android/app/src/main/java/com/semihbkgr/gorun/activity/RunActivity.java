package com.semihbkgr.gorun.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.R;

public class RunActivity extends AppCompatActivity {

    private static final String TAG = RunActivity.class.getName();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        //MenuBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Run");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Components
        findViewById(R.id.buttonNew).setOnClickListener(this::onButtonNewClickListener);
        findViewById(R.id.buttonSnippet).setOnClickListener(this::onButtonSnippetClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
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

    private void onButtonNewClickListener(View v) {
        Log.i(TAG, "onNewButtonClickListener: button clicked");
        startActivity(new Intent(this, EditorActivity.class));
    }

    private void onButtonSnippetClickListener(View v) {
        /*
        Log.i(TAG, "onButtonSnippetClickListener: button clicked");
        if (!AppSetting.instance.appState.hasInternetConnection()){
            AppSetting.instance.updateAllState();
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        } else if (AppSetting.instance.appState.getServerStateType() == ServerStateType.DOWN){
            AppSetting.instance.updateAllState();
            Toast.makeText(this, "Server is down", Toast.LENGTH_LONG).show();
        }

         */
    }

}