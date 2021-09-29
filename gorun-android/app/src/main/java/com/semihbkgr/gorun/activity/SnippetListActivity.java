package com.semihbkgr.gorun.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.AppConstant;
import com.semihbkgr.gorun.snippet.Snippet;
import com.semihbkgr.gorun.snippet.SnippetInfo;
import com.semihbkgr.gorun.util.SnippetInfoArrayAdapter;
import com.semihbkgr.gorun.util.http.ResponseCallback;

import java.util.List;

public class SnippetListActivity extends AppCompatActivity {

    private static final String TAG= SnippetListActivity.class.getName();

    private ListView snippetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet);
        snippetListView=findViewById(R.id.snippetListView);

        AppContext.instance().snippetService.getAllSnippetInfosAsync(new ResponseCallback<List<SnippetInfo>>() {
            @Override
            public void onResponse(List<SnippetInfo> data) {
                ArrayAdapter<SnippetInfo> snippetArrayAdapter= new SnippetInfoArrayAdapter(getApplicationContext(),data);
                snippetListView.setAdapter(snippetArrayAdapter);
            }

            @Override
            public void onFailure(Exception e) {
                show
            }
        });
        snippetListView.setOnItemClickListener(this::onSnippetListViewItemClick);
    }

    private void onSnippetListViewItemClick(AdapterView<?> parent, View view, int position, long id){
        Log.i(TAG, "onCreate: SnippetTextView item selected");
        SnippetInfo snippetInfo=(SnippetInfo)snippetListView.getAdapter().getItem(position);
        Intent intent=new Intent(this,SnippetActivity.class);
        intent.putExtra(AppConstant.Value.INTENT_SNIPPET_ID_NAME,snippetInfo.id);
        startActivity(intent);
    }

}