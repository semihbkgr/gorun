package com.semihbg.gorun;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    private Button runButton;
    private Button tutorialButton;

    private volatile boolean backPressFirstClick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Find Views
        runButton=findViewById(R.id.buttonRun);
        tutorialButton=findViewById(R.id.buttonTutorial);

        //Set button click listeners
        runButton.setOnClickListener(this::onButtonRunClick);
        tutorialButton.setOnClickListener(this::onButtonTutorialClick);

    }

    private void onButtonRunClick(View view){
        Intent intent=new Intent(this,RunActivity.class);
        startActivity(intent);
    }

    private void onButtonTutorialClick(View view){
        Intent intent=new Intent(this,TutorialActivity.class);
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

}