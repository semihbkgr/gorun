package com.semihbkgr.gorun.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.AppConstant;
import com.semihbkgr.gorun.message.Command;
import com.semihbkgr.gorun.run.CodeRunContext;
import com.semihbkgr.gorun.setting.AppSetting;
import com.semihbkgr.gorun.setting.ServerStateType;
import com.semihbkgr.gorun.util.TextChangeHandler;
import com.semihbkgr.gorun.util.TextChangeListener;
import com.semihbkgr.gorun.editor.CodeEditor;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = EditorActivity.class.getName();
    private static final int CODE_EDITOR_UPDATE_DELAY_MS = 500;

    private CodeEditor codeEditor;
    private EditText consoleEditText;

    private Button runButton;
    private Button consoleButton;

    private TextView consoleTextView;

    //Code shortcuts buttons
    private Button leftBraceButton;
    private Button rightBraceButton;
    private Button leftCurlyBraceButton;
    private Button rightCurlyBraceButton;
    private Button quoteButton;
    private Button tabButton;

    private TextChangeHandler consoleTextChangeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //-Find Views
        //CodeEditor
        codeEditor = findViewById(R.id.codeEditText);
        //Console
        consoleTextView = findViewById(R.id.outputTextView);
        consoleEditText = findViewById(R.id.inputEditText);
        consoleButton = findViewById(R.id.inputButton);
        //Run Button
        runButton = findViewById(R.id.runButton);
        //Shortcuts buttons
        leftBraceButton = findViewById(R.id.leftBraceButton);
        rightBraceButton = findViewById(R.id.rightBraceButton);
        leftCurlyBraceButton = findViewById(R.id.leftCurlyBraceButton);
        rightCurlyBraceButton = findViewById(R.id.rightCurlyBraceButton);
        quoteButton = findViewById(R.id.quoteButton);
        tabButton = findViewById(R.id.tabButton);

        //-Set Listeners
        //Console
        consoleButton.setOnClickListener(this::onConsoleButtonClicked);
        consoleTextView.setOnClickListener(this::onConsoleTextViewClicked);
        //Run Button
        runButton.setOnClickListener(this::onRunButtonClicked);
        //Shortcuts Buttons
        leftBraceButton.setOnClickListener(this::onLeftBraceButtonClicked);
        rightBraceButton.setOnClickListener(this::onRightBraceButtonClicked);
        leftCurlyBraceButton.setOnClickListener(this::onLeftCurlyBraceButtonClicked);
        rightCurlyBraceButton.setOnClickListener(this::onRightCurlyBraceButtonClicked);
        quoteButton.setOnClickListener(this::onQuoteButtonClicked);
        tabButton.setOnClickListener(this::onTabButtonClicked);

        //Console output update by delay
        consoleTextChangeHandler = new TextChangeHandler(CODE_EDITOR_UPDATE_DELAY_MS);
        consoleTextChangeHandler.addListener((event, text) -> runOnUiThread(() -> {
            if (event == TextChangeListener.Event.UPDATE) this.consoleTextView.setText(text);
            else this.consoleTextView.setText(consoleTextView.getText().toString().concat(text));
        }));
        CodeRunContext.instance.getCodeRunWebSocketSession().addMessageConsumer(message -> {
            Log.i(TAG, "onCreate: Message : " + message.command + " - " + message.body);
            if (message.command == Command.OUTPUT)
                this.consoleTextChangeHandler.append(message.body);
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        String code = getIntent().getStringExtra(AppConstant.Value.INTENT_EXTRA_SNIPPET_CODE);
        if (code != null) {
            Log.i(TAG, "onStart: Activity started with code");
            codeEditor.setText(code);
        } else
            Log.i(TAG, "onStart: Activity started with no code");
    }

    private void onRunButtonClicked(View v) {
        //TODO add socket connection control
        //TODO avoid button click spam
        if (!AppSetting.instance.appState.hasInternetConnection()) {
            AppSetting.instance.updateAllState();
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        } else if (AppSetting.instance.appState.getServerStateType() == ServerStateType.DOWN) {
            AppSetting.instance.updateAllState();
            Toast.makeText(this, "Server is down", Toast.LENGTH_LONG).show();
        } else {
            String code = codeEditor.getText().toString();
            consoleTextChangeHandler.clear();
            CodeRunContext.instance.run(code);
        }

    }

    private void onConsoleButtonClicked(View v) {
        String command = consoleEditText.getText().toString();
        consoleEditText.setText("");
        CodeRunContext.instance.send(command);
    }

    private void onConsoleTextViewClicked(View v) {
        this.consoleEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(consoleEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    //Code shortcuts button click listener methods
    private void onLeftBraceButtonClicked(View view) {
        Log.v(TAG, "onLeftBraceButtonClicked: button has been clicked");
        codeEditor.addText("(");
    }

    private void onRightBraceButtonClicked(View view) {
        Log.v(TAG, "onRightBraceButtonClicked: button has been clicked");
        codeEditor.addText(")");
    }

    private void onLeftCurlyBraceButtonClicked(View view) {
        Log.v(TAG, "onLeftCurlyBraceButtonClicked: button has been clicked");
        codeEditor.addText("{");
    }

    private void onRightCurlyBraceButtonClicked(View view) {
        Log.v(TAG, "onRightCurlyBraceButtonClicked: button has been clicked");
        codeEditor.addText("}");
    }

    private void onQuoteButtonClicked(View view) {
        Log.v(TAG, "onQuoteButtonClicked: button has been clicked");
        codeEditor.quote();
    }

    private void onTabButtonClicked(View view) {
        Log.v(TAG, "onTabButtonClicked: button has been clicked");
        codeEditor.addText("\t");
    }
    //----------------------------------------------

}