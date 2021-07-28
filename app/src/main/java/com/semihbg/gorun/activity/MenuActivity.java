package com.semihbg.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppConstant;
import com.semihbg.gorun.util.ExpirationTimer;

public class MenuActivity extends AppCompatActivity {

    private final ExpirationTimer backTimer =
            new ExpirationTimer(AppConstant.BACK_BUTTON_TIME_MS, "MenuActivityBackTimer");
    private Button buttonEditor;
    private Button buttonTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonEditor = findViewById(R.id.buttonEditor);
        buttonEditor.setOnClickListener(this::onButtonEditorClicked);
        buttonTutorial = findViewById(R.id.buttonTutorial);
        buttonTutorial.setOnClickListener(this::onButtonTutorialClicked);

    }

    @Override
    protected void onStop() {
        super.onStop();
        backTimer.stop();
    }

    private void onButtonEditorClicked(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private void onButtonTutorialClicked(View view) {
        Intent intent = new Intent(this, SubjectActivity.class);
        startActivity(intent);
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