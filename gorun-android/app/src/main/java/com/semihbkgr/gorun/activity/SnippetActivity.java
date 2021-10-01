package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.util.http.ResponseCallback;

public class SnippetActivity extends AppCompatActivity {

    private static final String TAG = SnippetActivity.class.getName();

    private TextView titleTextView;
    private TextView explanationTextView;
    private TextView codeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet);

        titleTextView = findViewById(R.id.snippetTitle);
        explanationTextView = findViewById(R.id.snippetExplanation);
        codeTextView = findViewById(R.id.snippetCode);

        int snippetId = getIntent().getIntExtra(AppConstants.Values.INTENT_SNIPPET_ID_NAME, Integer.MIN_VALUE);
        Log.i(TAG, "onCreate: snippetId: " + snippetId);
        if (snippetId != Integer.MIN_VALUE) {
            AppContext.instance().snippetService.getSnippetAsync(snippetId, new ResponseCallback<Snippet>() {
                @Override
                public void onResponse(Snippet data) {
                    loadSnippet(data);
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() -> {
                        startActivity(new Intent(SnippetActivity.this, MenuActivity.class));
                        Toast.makeText(SnippetActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } else {
            Log.w(TAG, "onCreate: Illegal snippetId");
            Toast.makeText(this, "Illegal snippetId", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MenuActivity.class));
        }

    }

    private void loadSnippet(Snippet snippet) {
        titleTextView.setText(snippet.title);
        explanationTextView.setText(snippet.explanation);
        codeTextView.setText(snippet.code);
    }

}