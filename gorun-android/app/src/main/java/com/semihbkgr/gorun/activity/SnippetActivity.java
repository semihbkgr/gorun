package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.util.http.ResponseCallback;

public class SnippetActivity extends AppCompatActivity {

    private static final String TAG=SnippetActivity.class.getName();

    private TextView titleTextView;
    private TextView briefTextView;
    private TextView explanationTextView;
    private TextView codeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snippetActivity);

        int snippetId=getIntent().getIntExtra(AppConstants.Values.INTENT_SNIPPET_ID_NAME,Integer.MIN_VALUE);
        Log.i(TAG, "onCreate: snippetId: "+snippetId);
        if(snippetId!=Integer.MIN_VALUE){
            AppContext.instance().snippetService.getSnippetAsync(snippetId, new ResponseCallback<Snippet>() {
                @Override
                public void onResponse(Snippet data) {
                    loadSnippet(data);
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(()-> startActivity(new Intent(SnippetActivity.this,MenuActivity.class)));
                }
            });
        } else
            startActivity(new Intent(this,MenuActivity.class));

    }

    private void loadSnippet(Snippet snippet){
        titleTextView.setText(snippet.title);
        briefTextView.setText(snippet.brief);
        explanationTextView.setText(snippet.explanation);
        codeTextView.setText(snippet.code);
    }

}