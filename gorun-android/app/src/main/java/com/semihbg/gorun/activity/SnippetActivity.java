package com.semihbg.gorun.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppConstant;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.snippet.Snippet;
import com.semihbg.gorun.view.adapter.SnippetArrayAdapter;

import java.util.List;

public class SnippetActivity extends AppCompatActivity {

    private static final String TAG=SnippetActivity.class.getName();

    private ListView snippetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet);
        snippetListView=findViewById(R.id.snippetListView);
        if(AppContext.instance().snippetService.isAvailable()){
            List<Snippet> snippetList=AppContext.instance().snippetService.getSnippets();
            ArrayAdapter<Snippet> snippetArrayAdapter=new SnippetArrayAdapter(getApplicationContext(),snippetList);
            snippetListView.setAdapter(snippetArrayAdapter);
        }else{
            AppContext.instance().snippetService.getSnippetsAsync((snippetList)-> runOnUiThread(()->{
                ArrayAdapter<Snippet> snippetArrayAdapter=new SnippetArrayAdapter(getApplicationContext(),snippetList);
                snippetListView.setAdapter(snippetArrayAdapter);
            }));
        }
        snippetListView.setOnItemClickListener(this::onSnippetListViewItemClick);
    }

    private void onSnippetListViewItemClick(AdapterView<?> parent, View view, int position, long id){
        Log.i(TAG, "onCreate: SnippetTextView item selected");
        Snippet snippet=(Snippet)snippetListView.getAdapter().getItem(position);
        Intent intent=new Intent(this,EditorActivity.class);
        intent.putExtra(AppConstant.INTENT_EXTRA_SNIPPET_CODE,snippet.code);
        startActivity(intent);
    }


}