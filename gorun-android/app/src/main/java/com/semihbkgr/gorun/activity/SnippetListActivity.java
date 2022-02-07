package com.semihbkgr.gorun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;
import com.semihbkgr.gorun.snippet.SnippetService;
import com.semihbkgr.gorun.util.ActivityUtils;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class SnippetListActivity extends AppCompatActivity {

    private static final String TAG = SnippetListActivity.class.getName();

    private ListView snippetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_list);

        ActivityUtils.loadActionBar(this, "GoRun");

        snippetListView = findViewById(R.id.snippetListView);
        snippetListView.setOnItemClickListener(this::onSnippetListViewItemClick);

    }

    @Override
    protected void onStart() {
        super.onStart();
        AppContext.instance().snippetService.getAllSnippetInfosAsync(new ResponseCallback<List<SnippetInfo>>() {
            @Override
            public void onResponse(List<SnippetInfo> data) {
                AppContext.instance().executorService.execute(() -> {
                    List<Integer> savedIdList = AppContext.instance().snippetService.getAllSavedId();
                    List<SnippetInfoViewModelHolder> snippetInfoViewModelHolderList = data.parallelStream().map(snippetInfo -> {
                        if (savedIdList.contains(snippetInfo.id))
                            return SnippetInfoViewModelHolder.downloadedOf(snippetInfo, false);
                        else
                            return SnippetInfoViewModelHolder.nonDownloadedOf(snippetInfo, false);
                    }).sorted().collect(Collectors.toList());
                    runOnUiThread(() -> snippetListView.setAdapter(new SnippetInfoArrayAdapter(getApplicationContext(), snippetInfoViewModelHolderList,
                            AppContext.instance().snippetService, AppContext.instance().executorService)));
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> Toast.makeText(SnippetListActivity.this, "Connection error", Toast.LENGTH_SHORT).show());
                AppContext.instance().executorService.execute(() -> {
                    List<SnippetInfo> savedSnippetInfoList = AppContext.instance().snippetService.getAllSavedSnippetInfos();
                    List<SnippetInfoViewModelHolder> snippetInfoViewModelHolderList = savedSnippetInfoList.parallelStream()
                            .map((SnippetInfo snippetInfo) -> SnippetInfoViewModelHolder.downloadedOf(snippetInfo, true))
                            .sorted()
                            .collect(Collectors.toList());
                    runOnUiThread(() -> snippetListView.setAdapter(new SnippetInfoArrayAdapter(getApplicationContext(), snippetInfoViewModelHolderList,
                            AppContext.instance().snippetService, AppContext.instance().executorService)));
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

    private static class SnippetInfoArrayAdapter extends ArrayAdapter<SnippetInfoViewModelHolder> {

        private static final String TAG = SnippetInfoArrayAdapter.class.getName();

        private final SnippetService snippetService;
        private final Executor executor;

        public SnippetInfoArrayAdapter(@NonNull Context context, @NonNull List<SnippetInfoViewModelHolder> objects,
                                       @NonNull SnippetService snippetService, @NonNull Executor executor) {
            super(context, 0, objects);
            this.snippetService = snippetService;
            this.executor = executor;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_snippet_list_view, parent, false);
            SnippetInfoViewModelHolder snippetInfoViewModelHolder = getItem(position);
            SnippetInfo snippetInfo = snippetInfoViewModelHolder.snippetInfo;
            if (snippetInfo == null)
                return convertView;
            TextView titleTextView = convertView.findViewById(R.id.titleTextView);
            titleTextView.setText(snippetInfo.title);
            TextView briefTextView = convertView.findViewById(R.id.briefTextView);
            briefTextView.setText(snippetInfo.brief);
            ImageButton saveOrDeleteImageButton = convertView.findViewById(R.id.saveOrDeleteImageButton);
            saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(snippetInfoViewModelHolder.isDownloaded() ? R.drawable.delete : R.drawable.download));
            saveOrDeleteImageButton.setOnClickListener(v -> {
                saveOrDeleteImageButton.setClickable(false);
                saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.waiting));
                if (snippetInfoViewModelHolder.isDownloaded()) {
                    executor.execute(() -> {
                        try {
                            snippetService.delete(snippetInfo.id);
                            Log.i(TAG, "getView: snippet has been deleted, snippetId: " + snippetInfo.id);
                            snippetInfoViewModelHolder.setDownloaded(false);
                            new Handler(getContext().getMainLooper()).post(() -> {
                                saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.download));
                                Toast.makeText(getContext(), String.format("Snippet '%s' deleted", snippetInfo.title), Toast.LENGTH_SHORT).show();
                                saveOrDeleteImageButton.setClickable(true);
                                if (snippetInfoViewModelHolder.removeFromListWhenDeleted)
                                    remove(snippetInfoViewModelHolder);
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "getView: error while deleting snippet, snippetId: " + snippetInfo.id, e);
                            new Handler(getContext().getMainLooper()).post(() -> {
                                saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.delete));
                                Toast.makeText(getContext(), "Error while deleting", Toast.LENGTH_SHORT).show();
                                saveOrDeleteImageButton.setClickable(true);
                            });
                        }
                    });
                } else {
                    saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.waiting));
                    AppContext.instance().snippetService.getSnippetAsync(snippetInfo.id, new ResponseCallback<Snippet>() {
                        @Override
                        public void onResponse(Snippet data) {
                            try {
                                AppContext.instance().snippetService.save(data);
                                Log.i(TAG, "getView: snippet has been downloaded, snippetId: " + snippetInfo.id);
                                snippetInfoViewModelHolder.setDownloaded(true);
                                new Handler(getContext().getMainLooper()).post(() -> {
                                    saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.delete));
                                    Toast.makeText(getContext(), String.format("Snippet '%s' downloaded", snippetInfo.title), Toast.LENGTH_SHORT).show();
                                    saveOrDeleteImageButton.setClickable(true);
                                });
                            } catch (Exception e) {
                                Log.e(TAG, "getView: error while downloading snippet, snippetId: " + snippetInfo.id, e);
                                new Handler(getContext().getMainLooper()).post(() -> {
                                    saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.download));
                                    Toast.makeText(getContext(), "Error while downloading", Toast.LENGTH_SHORT).show();
                                    saveOrDeleteImageButton.setClickable(true);
                                });
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e(TAG, "onFailure: error while downloading snippet, snippetId: " + snippetInfo.id, e);
                            new Handler(getContext().getMainLooper()).post(() -> {
                                saveOrDeleteImageButton.setImageDrawable(getContext().getDrawable(R.drawable.download));
                                Toast.makeText(getContext(), String.format("Snippet '%s' downloaded", snippetInfo.title), Toast.LENGTH_SHORT).show();
                                saveOrDeleteImageButton.setClickable(true);
                            });
                        }
                    });
                }

            });
            return convertView;
        }

    }

    private static class SnippetInfoViewModelHolder implements Comparable<SnippetInfoViewModelHolder> {

        public final SnippetInfo snippetInfo;
        public final boolean removeFromListWhenDeleted;
        private boolean downloaded;

        private SnippetInfoViewModelHolder(SnippetInfo snippetInfo, boolean downloaded, boolean removeFromListWhenDeleted) {
            this.snippetInfo = snippetInfo;
            this.downloaded = downloaded;
            this.removeFromListWhenDeleted = removeFromListWhenDeleted;
        }

        public static SnippetInfoViewModelHolder downloadedOf(@NonNull SnippetInfo snippetInfo, boolean removeFromListWhenDeleted) {
            return new SnippetInfoViewModelHolder(snippetInfo, true, removeFromListWhenDeleted);
        }

        public static SnippetInfoViewModelHolder nonDownloadedOf(@NonNull SnippetInfo snippetInfo, boolean removeFromListWhenDeleted) {
            return new SnippetInfoViewModelHolder(snippetInfo, false, removeFromListWhenDeleted);
        }

        public boolean isDownloaded() {
            return downloaded;
        }

        public void setDownloaded(boolean downloaded) {
            this.downloaded = downloaded;
        }

        @Override
        public int compareTo(SnippetInfoViewModelHolder o) {
            return snippetInfo.order - o.snippetInfo.order;
        }

    }


}