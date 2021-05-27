package com.semihbg.gorun;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.run.CodeRunClient;
import com.semihbg.gorun.run.DefaultCodeRunClient;
import com.semihbg.gorun.util.InputStreamHandler;

import java.io.InputStream;

public class EditorActivity extends AppCompatActivity {

    private Button runButton;
    private EditText codeEditText;
    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //Find views
        runButton=findViewById(R.id.runButton);
        codeEditText=findViewById(R.id.codeEditText);
        outputTextView=findViewById(R.id.outputTextView);

        //Set view listener
        runButton.setOnClickListener(this::onRunButtonClicked);

    }


    private void onRunButtonClicked(View v){
        String code=codeEditText.getText().toString();
        outputTextView.setText("");
        CodeRunClient.DEFAULT.run(code,(i)->{
            InputStreamHandler.handle(()->i, outputTextView,this);
        });
    }


}