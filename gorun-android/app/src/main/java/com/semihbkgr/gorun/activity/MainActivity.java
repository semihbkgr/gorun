package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.AppConstant;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.setting.AppSetting;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

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
                }, AppConstant.Value.LOGO_SCREEN_TIME_MS);

    }

}
