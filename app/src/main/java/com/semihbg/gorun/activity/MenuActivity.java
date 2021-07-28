package com.semihbg.gorun.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.R;

import java.io.*;

public class MenuActivity extends AppCompatActivity {

    private Button runButton;
    private Button tutorialButton;
    private Button docButton;

    private volatile boolean backPressFirstClick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Find Views
        runButton=findViewById(R.id.buttonRun);
        tutorialButton=findViewById(R.id.buttonTutorial);
        docButton=findViewById(R.id.buttonDoc);

        //Set button click listeners
        runButton.setOnClickListener(this::onButtonRunClick);

    }

    private void onButtonRunClick(View view){
        Intent intent=new Intent(this,RunActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if(backPressFirstClick){
            super.onBackPressed();
            return;
        }
        backPressFirstClick=true;
        new Handler().postDelayed(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            backPressFirstClick=false;
        },1000);
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

}