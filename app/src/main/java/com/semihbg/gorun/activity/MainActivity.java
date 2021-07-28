package com.semihbg.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppConstant;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.setting.AppSetting;
import com.semihbg.gorun.tutorial.Subject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG=MainActivity.class.getName();

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
                }, AppConstant.LOGO_SCREEN_TIME_MS);

        if(AppContext.instance().tutorialService.subjectCount()==0){
            try {
                Subject[] subjects=AppContext.instance().appSourceHelper.readAsset(AppConstant.SUBJECT_ASSET_FILE_NAME,Subject[].class);
                long count=AppContext.instance().tutorialService.saveAll(Arrays.asList(subjects));
                Log.i(TAG, "onCreate: Subjects have see saved successfully, count: "+count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
