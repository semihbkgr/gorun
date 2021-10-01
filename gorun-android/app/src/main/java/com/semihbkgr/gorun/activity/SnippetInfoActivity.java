package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.SnippetInfo;
import com.semihbkgr.gorun.snippet.view.SnippetInfoArrayAdapter;
import com.semihbkgr.gorun.snippet.view.SnippetInfoViewModelHolder;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.List;
import java.util.stream.Collectors;

public class SnippetInfoActivity extends AppCompatActivity {

    private static final String TAG = SnippetInfoActivity.class.getName();

    private ListView snippetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_info);

        snippetListView = findViewById(R.id.snippetListView);
        snippetListView.setOnItemClickListener(this::onSnippetListViewItemClick);

        AppContext.instance().snippetService.getAllSnippetInfosAsync(new ResponseCallback<List<SnippetInfo>>() {
            @Override
            public void onResponse(List<SnippetInfo> data) {
                AppContext.instance().executorService.execute(() -> {
                    List<Integer> savedIdList = AppContext.instance().snippetService.getAllSavedId();
                    List<SnippetInfoViewModelHolder> snippetInfoViewModelHolderList = data.parallelStream().map(snippetInfo -> {
                        if (savedIdList.contains(snippetInfo.id))
                            return SnippetInfoViewModelHolder.downloadedOf(snippetInfo);
                        else
                            return SnippetInfoViewModelHolder.nonDownloadedOf(snippetInfo);
                    }).sorted().collect(Collectors.toList());
                    runOnUiThread(() -> snippetListView.setAdapter(new SnippetInfoArrayAdapter(getApplicationContext(), snippetInfoViewModelHolderList)));
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> Toast.makeText(SnippetInfoActivity.this, "Connection error", Toast.LENGTH_SHORT).show());
                AppContext.instance().executorService.execute(() -> {
                    List<SnippetInfo> savedSnippetInfoList = AppContext.instance().snippetService.getAllSavedSnippetInfos();
                    List<SnippetInfoViewModelHolder> snippetInfoViewModelHolderList = savedSnippetInfoList.parallelStream()
                            .map(SnippetInfoViewModelHolder::downloadedOf)
                            .sorted()
                            .collect(Collectors.toList());
                    runOnUiThread(() -> snippetListView.setAdapter(new SnippetInfoArrayAdapter(getApplicationContext(), snippetInfoViewModelHolderList)));
                });
            }
        });

    }

    private void onSnippetListViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onCreate: SnippetTextView item selected");
        SnippetInfo snippetInfo = ((SnippetInfoViewModelHolder) snippetListView.getAdapter().getItem(position)).snippetInfo;
        Intent intent = new Intent(this, SnippetActivity.class);
        intent.putExtra(AppConstants.Values.INTENT_SNIPPET_ID_NAME, snippetInfo.id);
        startActivity(intent);
    }

}