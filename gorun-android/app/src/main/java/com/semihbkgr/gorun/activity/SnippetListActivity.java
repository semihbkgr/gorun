package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;
import com.semihbkgr.gorun.util.SnippetInfoArrayAdapter;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SnippetListActivity extends AppCompatActivity {

    private static final String TAG = SnippetListActivity.class.getName();
    private final Map<Integer, SnippetInfo> idSnippetInfoMap = new ConcurrentHashMap<>();
    private ListView snippetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_list);

        snippetListView = findViewById(R.id.snippetListView);

        AppContext.instance().executorService.execute(() -> {
            List<SnippetInfo> savedSnippetInfoList = AppContext.instance().snippetService.getAllSavedSnippetInfos();
            savedSnippetInfoList.removeIf(snippetInfo -> {
                if (idSnippetInfoMap.containsKey(snippetInfo.id)) {
                    if (!snippetInfo.equals(idSnippetInfoMap.get(snippetInfo.id)))
                        AppContext.instance().snippetService.getSnippetAsync(snippetInfo.id, new ResponseCallback<Snippet>() {
                            @Override
                            public void onResponse(Snippet data) {
                                AppContext.instance().snippetService.save(data);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    return true;
                }
                return false;
            });
            runOnUiThread(() -> {

                SnippetInfoArrayAdapter arrayAdapter = new SnippetInfoArrayAdapter(getApplicationContext(), new ArrayList<>(idSnippetInfoMap.values()));
                snippetListView.setAdapter(arrayAdapter);
            });
        });


        AppContext.instance().snippetService.getAllSnippetInfosAsync(new ResponseCallback<List<SnippetInfo>>() {
            @Override
            public void onResponse(List<SnippetInfo> data) {
                data.forEach(snippetInfo -> {
                    if (idSnippetInfoMap.containsKey(snippetInfo.id) && !idSnippetInfoMap.get(snippetInfo.id).equals(snippetInfo)) {
                        AppContext.instance().snippetService.getSnippetAsync(snippetInfo.id, new ResponseCallback<Snippet>() {
                            @Override
                            public void onResponse(Snippet data) {
                                AppContext.instance().snippetService.save(data);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
                runOnUiThread(() -> {
                    SnippetInfoArrayAdapter arrayAdapter = new SnippetInfoArrayAdapter(getApplicationContext(), new ArrayList<>(idSnippetInfoMap.values()));
                    snippetListView.setAdapter(arrayAdapter);
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        snippetListView.setOnItemClickListener(this::onSnippetListViewItemClick);
    }

    private void onSnippetListViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onCreate: SnippetTextView item selected");
        SnippetInfo snippetInfo = (SnippetInfo) snippetListView.getAdapter().getItem(position);
        Intent intent = new Intent(this, SnippetActivity.class);
        intent.putExtra(AppConstants.Values.INTENT_SNIPPET_ID_NAME, snippetInfo.id);
        startActivity(intent);
    }

}