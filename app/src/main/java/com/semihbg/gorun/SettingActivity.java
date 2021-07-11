package com.semihbg.gorun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.setting.AppSetting;
import com.semihbg.gorun.util.StateUtils;

import java.util.concurrent.ForkJoinPool;

public class SettingActivity extends AppCompatActivity {

    private TextView internetConnectionValueTextView;
    private TextView serverStateValueTextView;

    //TODO setting MenuBar remove setting button
    //TODO MenuBar multiple click avoid
    //TODO General thread pool instead of common pool

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //MenuBar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Setting");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Components
        internetConnectionValueTextView=findViewById(R.id.internetConnectionValueTextView);
        serverStateValueTextView=findViewById(R.id.serverStateValueTextView);

        updateStateComponentsText();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ForkJoinPool.commonPool().execute(()-> {
            AppSetting.instance.appState.setInternetConnection(StateUtils.hasInternetConnection());
            this.runOnUiThread(this::updateStateComponentsText);
        });
        ForkJoinPool.commonPool().execute(()-> {
            AppSetting.instance.appState.setServerStateType(StateUtils.serverStateType());
            this.runOnUiThread(this::updateStateComponentsText);
        });
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateStateComponentsText(){
        if(AppSetting.instance.appState.isInternetConnection()) internetConnectionValueTextView.setText("Online");
        else internetConnectionValueTextView.setText("Offline");
        serverStateValueTextView.setText(AppSetting.instance.appState.getServerStateType().value);
    }

}