package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("GoRun");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}