package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.util.ActivityUtils;
import com.semihbkgr.gorun.util.http.ResponseCallback;

public class SnippetActivity extends AppCompatActivity {

    private static final String TAG = SnippetActivity.class.getName();

    private TextView titleTextView;
    private TextView explanationTextView;
    private TextView codeTextView;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet);

        ActivityUtils.loadActionBar(this, "GoRun");

        titleTextView = findViewById(R.id.snippetTitle);
        explanationTextView = findViewById(R.id.snippetExplanation);
        codeTextView = findViewById(R.id.snippetCode);
        startButton = findViewById(R.id.startButton);

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
                        startActivity(new Intent(SnippetActivity.this, MainActivity.class));
                        Toast.makeText(SnippetActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } else {
            Log.w(TAG, "onCreate: Illegal snippetId");
            Toast.makeText(this, "Illegal snippetId", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void loadSnippet(Snippet snippet) {
        titleTextView.setText(snippet.title);
        explanationTextView.setText(snippet.explanation);
        codeTextView.setText(snippet.code);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(AppConstants.Values.INTENT_SNIPPET_CODE_NAME, snippet.code);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_default, menu);
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