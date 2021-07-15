package com.semihbg.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.setting.AppSetting;

public class MainActivity extends AppCompatActivity {

    //TODO go logo gif

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContext.initialize(getApplicationContext());
        AppSetting.instance.updateAllState();

        new Handler(getMainLooper())
                .postDelayed(() -> {
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }, 10);

    }

}
