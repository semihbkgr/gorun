package com.semihbg.gorun;

import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.run.CodeRunContext;
import com.semihbg.gorun.view.code.CodeEditText;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG=EditorActivity.class.getName();

    private CodeEditText codeEditText;

    private Button runButton;

    private TextView outputTextView;

    //Code shortcuts buttons
    private Button leftBraceButton;
    private Button rightBraceButton;
    private Button leftCurlyBraceButton;
    private Button rightCurlyBraceButton;
    private Button quoteButton;
    private Button tabButton;

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

        CodeRunContext.instance.getCodeRunWebSocketSession().addMessageConsumer(message->{
            System.out.println("----------------------------------");
            System.out.println(message.command+" - "+message.body);
            System.out.println("----------------------------------");
            if(message.body!=null){
                runOnUiThread(()->{
                    outputTextView.setText(outputTextView.getText().toString().concat(message.body));
                });
            }
        });

        //Assign code shortcuts buttons
        leftBraceButton=findViewById(R.id.leftBraceButton);
        rightBraceButton=findViewById(R.id.rightBraceButton);
        leftCurlyBraceButton=findViewById(R.id.leftCurlyBraceButton);
        rightCurlyBraceButton=findViewById(R.id.rightCurlyBraceButton);
        quoteButton=findViewById(R.id.quoteButton);
        tabButton=findViewById(R.id.tabButton);

        //Set code shortcuts buttons' listener
        leftBraceButton.setOnClickListener(this::onLeftBraceButtonClicked);
        rightBraceButton.setOnClickListener(this::onRightBraceButtonClicked);
        leftCurlyBraceButton.setOnClickListener(this::onLeftCurlyBraceButtonClicked);
        rightCurlyBraceButton.setOnClickListener(this::onRightCurlyBraceButtonClicked);
        quoteButton.setOnClickListener(this::onQuoteButtonClicked);
        tabButton.setOnClickListener(this::onTabButtonClicked);

    }

    @Override
    protected void onStart() {
        super.onStart();
        String code=getIntent().getStringExtra(AppConstants.INTENT_EXTRA_SNIPPET_CODE);
        if(code!=null){
            Log.i(TAG, "onStart: Activity started with code");
            codeEditText.setText(code);}
        else
            Log.i(TAG, "onStart: Activity started with no code");
    }

    private void onRunButtonClicked(View v){
        String code=codeEditText.getText().toString();
        outputTextView.setText("");
        CodeRunContext.instance.run(code);
    }


    //Code shortcuts button click listener methods

    private void onLeftBraceButtonClicked(View view){
        Log.v(TAG, "onLeftBraceButtonClicked: button has been clicked");
        codeEditText.addText("(");
    }

    private void onRightBraceButtonClicked(View view){
        Log.v(TAG, "onRightBraceButtonClicked: button has been clicked");
        codeEditText.addText(")");
    }

    private void onLeftCurlyBraceButtonClicked(View view){
        Log.v(TAG, "onLeftCurlyBraceButtonClicked: button has been clicked");
        codeEditText.addText("{");
    }

    private void onRightCurlyBraceButtonClicked(View view){
        Log.v(TAG, "onRightCurlyBraceButtonClicked: button has been clicked");
        codeEditText.addText("}");
    }

    private void onQuoteButtonClicked(View view){
        Log.v(TAG, "onQuoteButtonClicked: button has been clicked");
        codeEditText.quote();
    }

    private void onTabButtonClicked(View view){
        Log.v(TAG, "onTabButtonClicked: button has been clicked");
        codeEditText.addText("\t");
    }


}