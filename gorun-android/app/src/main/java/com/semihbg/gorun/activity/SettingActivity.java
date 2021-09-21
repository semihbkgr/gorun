package com.semihbg.gorun.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.setting.AppSetting;
import com.semihbg.gorun.setting.ServerStateType;

import java.util.concurrent.Callable;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG=SettingActivity.class.getName();

    private TextView internetConnectionValueTextView;
    private TextView serverStateValueTextView;
    private Button buttonClearSubjects;

    //TODO setting MenuBar remove setting button
    //TODO MenuBar multiple click avoid
    //TODO General thread pool instead of common pool

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

        buttonClearSubjects=findViewById(R.id.buttonClearSubjects);
        buttonClearSubjects.setOnClickListener(this::onButtonClearSubjectClicked);
        internetConnectionValueTextView=findViewById(R.id.internetConnectionValueTextView);
        serverStateValueTextView=findViewById(R.id.serverStateValueTextView);

        updateStateComponentsText();

    }

    @Override
    protected void onStart() {
        super.onStart();
        AppSetting.instance.updateInternetConnection(this::updateInternetConnectionValueTextView);
        AppSetting.instance.updateServerState(this::updateServerStateValueTextView);
    }

    private void onButtonClearSubjectClicked(View v){
        Log.i(TAG, "onButtonClearSubjectClicked: Button clicked");
        AppContext.instance().listenedThreadPoolWrapper.listenedExecute(
                (Callable<Integer>) AppContext.instance().tutorialService::deleteAllSubjects,
                count->{
                    Log.i(TAG, "onButtonClearSubjectClicked: Subjects table cleared, Deleted row count: "+count);
                    runOnUiThread(()-> Toast.makeText(this,"Subjects table cleared, Deleted row count: "+count,Toast.LENGTH_SHORT).show());
                });
    }


    private void updateStateComponentsText(){
        if(AppSetting.instance.appState.hasInternetConnection()) internetConnectionValueTextView.setText("Online");
        else internetConnectionValueTextView.setText("Offline");
        serverStateValueTextView.setText(AppSetting.instance.appState.getServerStateType().value);
    }

    private void updateInternetConnectionValueTextView(boolean hasInternetConnection){
        if(hasInternetConnection) internetConnectionValueTextView.setText("Online");
        else internetConnectionValueTextView.setText("Offline");
    }

    private void updateServerStateValueTextView(ServerStateType serverStateType){
        serverStateValueTextView.setText(serverStateType.value);
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

}