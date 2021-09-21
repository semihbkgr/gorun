package com.semihbkgr.gorun.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.core.AppConstant;
import com.semihbkgr.gorun.core.AppContext;
import com.semihbkgr.gorun.tutorial.Subject;
import com.semihbkgr.gorun.view.adapter.SubjectArrayAdapter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class SubjectActivity extends AppCompatActivity {

    private static final String TAG=SubjectActivity.class.getName();

    private ListView listViewSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        listViewSubject = findViewById(R.id.listViewSubject);

        AppContext.instance().listenedThreadPoolWrapper.listenedExecute(
                (Callable<Long>)()->AppContext.instance().tutorialService.subjectCount(),
                count->{
                    if(count>0){//Get Subject from database
                        List<Subject> subjectList=AppContext.instance().tutorialService.findSubjects();
                        runOnUiThread(()-> listViewSubject.setAdapter(new SubjectArrayAdapter(this, subjectList)));
                    }else{//Get Subjects from source and insert them database
                        try{
                            Subject[] subjects=AppContext.instance().appSourceHelper.readAsset(AppConstant.SUBJECT_ASSET_FILE_NAME,Subject[].class);
                            boolean saved=AppContext.instance().tutorialService.saveAll(Arrays.asList(subjects));
                            if(saved) Log.i(TAG, "onCreate: Subjects have see saved successfully");
                            else Log.i(TAG, "onCreate: Subjects have not been saved");
                            List<Subject> subjectList=Arrays.asList(subjects);
                            runOnUiThread(()-> listViewSubject.setAdapter(new SubjectArrayAdapter(this, subjectList)));
                        } catch (IOException e) {
                            e.printStackTrace();
                            runOnUiThread(()-> Toast.makeText(this, "Subjects cannot be read from source", Toast.LENGTH_SHORT).show());
                        }
                    }
                });


    }


}