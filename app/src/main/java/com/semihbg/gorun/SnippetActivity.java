package com.semihbg.gorun;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.snippet.Snippet;
import com.semihbg.gorun.view.adapter.SnippetArrayAdapter;

import java.util.List;

public class SnippetActivity extends AppCompatActivity {

    private ListView snippetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet);
        snippetListView=findViewById(R.id.snippetListView);
        if(AppContext.snippetRepository.isCached()){
            List<Snippet> snippetList=AppContext.snippetRepository.getAllSnippetsBlock();
            ArrayAdapter<Snippet> snippetArrayAdapter=new SnippetArrayAdapter(getApplicationContext(),snippetList);
            snippetListView.setAdapter(snippetArrayAdapter);
        }else
            AppContext.snippetRepository.getAllSnippetsAsync((snippetList)-> runOnUiThread(()->{
                ArrayAdapter<Snippet> snippetArrayAdapter=new SnippetArrayAdapter(getApplicationContext(),snippetList);
                snippetListView.setAdapter(snippetArrayAdapter);
            }));
    }

}