package com.semihbg.gorun;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbg.gorun.setting.AppSetting;
import com.semihbg.gorun.util.StateUtils;

import java.net.InetAddress;
import java.util.concurrent.ForkJoinPool;

public class MainActivity extends AppCompatActivity {

    //TODO go logo gif

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppContext.initialize(getApplicationContext());
        new Handler(getMainLooper())
                .postDelayed(() -> {
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }, 10);

        ForkJoinPool.commonPool().execute(()-> AppSetting.instance.appState.setInternetConnection(StateUtils.hasInternetConnection()));
        ForkJoinPool.commonPool().execute(()-> AppSetting.instance.appState.setServerStateType(StateUtils.serverStateType()));

    }

}
