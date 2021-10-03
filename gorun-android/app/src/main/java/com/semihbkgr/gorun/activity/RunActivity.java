package com.semihbkgr.gorun.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.editor.CodeEditor;
import com.semihbkgr.gorun.message.Action;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.run.RunSessionObserver;
import com.semihbkgr.gorun.run.RunSessionStatus;
import com.semihbkgr.gorun.util.view.TextChangeHandler;
import com.semihbkgr.gorun.util.view.TextChangeListener;

public class RunActivity extends AppCompatActivity {

    private static final String TAG = RunActivity.class.getName();
    private static final int CODE_EDITOR_UPDATE_DELAY_MS = 500;

    private CodeEditor codeEditor;
    private EditText consoleEditText;
    private ImageButton runButton;
    private Button consoleButton;
    private TextView consoleTextView;
    private Button leftBraceButton;
    private Button rightBraceButton;
    private Button leftCurlyBraceButton;
    private Button rightCurlyBraceButton;
    private Button quoteButton;
    private Button tabButton;
    private TextChangeHandler consoleTextChangeHandler;
    private TextView infoTextView;

    private Toast connectingToast;

    private final RunSessionObserver runSessionObserver = status ->{
        Log.i(TAG, "unSessionObserver: status: "+status.name());
        runOnUiThread(() -> updateRunButton(status));
    };

    private final View.OnClickListener hasSessionOnClickListener = view -> {
        String code=codeEditor.getText().toString();
        AppContext.instance().runSessionManager.session().sendMessage(Message.of(Action.RUN,code));
    };

    private final View.OnClickListener creatingOnClickListener = view -> {
        // Show 'connecting' toast massage if it is not showing at that time.
        try{
            if(!connectingToast.getView().isShown()){
                connectingToast.show();
            }
        } catch (Exception e) {
            this.connectingToast = Toast.makeText(this,getString(R.string.connecting_toast_message),Toast.LENGTH_SHORT);
            connectingToast.show();
        }
    };

    private final View.OnClickListener noSessionOnClickListener = view -> {
        AppContext.instance().runSessionManager.connect();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        // Find and assign components.
        this.codeEditor = findViewById(R.id.codeEditText);
        this.consoleTextView = findViewById(R.id.outputTextView);
        this.consoleEditText = findViewById(R.id.inputEditText);
        this.consoleButton = findViewById(R.id.inputButton);
        this.runButton = findViewById(R.id.runButton);
        this.leftBraceButton = findViewById(R.id.leftBraceButton);
        this.rightBraceButton = findViewById(R.id.rightBraceButton);
        this.leftCurlyBraceButton = findViewById(R.id.leftCurlyBraceButton);
        this.rightCurlyBraceButton = findViewById(R.id.rightCurlyBraceButton);
        this.quoteButton = findViewById(R.id.quoteButton);
        this.tabButton = findViewById(R.id.tabButton);
        this.infoTextView=findViewById(R.id.infoTextView);

        this.connectingToast=Toast.makeText(this,getString(R.string.connecting_toast_message),Toast.LENGTH_SHORT);

        consoleButton.setOnClickListener(this::onConsoleButtonClicked);
        consoleTextView.setOnClickListener(this::onConsoleTextViewClicked);
        leftBraceButton.setOnClickListener(this::onLeftBraceButtonClicked);
        rightBraceButton.setOnClickListener(this::onRightBraceButtonClicked);
        leftCurlyBraceButton.setOnClickListener(this::onLeftCurlyBraceButtonClicked);
        rightCurlyBraceButton.setOnClickListener(this::onRightCurlyBraceButtonClicked);
        quoteButton.setOnClickListener(this::onQuoteButtonClicked);
        tabButton.setOnClickListener(this::onTabButtonClicked);

        consoleTextChangeHandler = new TextChangeHandler(CODE_EDITOR_UPDATE_DELAY_MS);
        consoleTextChangeHandler.addListener((event, text) -> runOnUiThread(() -> {
            if (event == TextChangeListener.Event.UPDATE) this.consoleTextView.setText(text);
            else this.consoleTextView.setText(consoleTextView.getText().toString().concat(text));
        }));

        AppContext.instance().runSessionManager.registerObserver(runSessionObserver);
        updateRunButton(AppContext.instance().runSessionManager.getStatus());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AppContext.instance().runSessionManager.getStatus() == RunSessionStatus.NO_SESSION)
            AppContext.instance().runSessionManager.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.instance().runSessionManager.unregisterObserver(runSessionObserver);
    }

    private void updateRunButton(RunSessionStatus status) {
        switch (status) {
            case HAS_SESSION:
                runButton.setOnClickListener(hasSessionOnClickListener);
                runButton.setImageDrawable(getDrawable(R.drawable.run));
                runButton.animate().cancel();
                runButton.clearAnimation();
                infoTextView.setTextColor(getColor(R.color.green));
                infoTextView.setText(getString(R.string.connected_info_text));
                AppContext.instance().runSessionManager.session().addMessageConsumer(message -> {
                    Log.i(TAG, "Message action: "+message.action.name()+", body: "+message.body);
                    if(message.action == Action.OUTPUT)
                        consoleTextView.append(message.body);
                });
                break;
            case CREATING:
                runButton.setOnClickListener(creatingOnClickListener);
                runButton.setImageDrawable(getDrawable(R.drawable.connecting));
                runButton.clearAnimation();
                runButton.animate().rotationBy(1800).setDuration(10000);
                infoTextView.setTextColor(getColor(R.color.black));
                infoTextView.setText(getString(R.string.connecting_info_text));
                break;
            case NO_SESSION:
                runButton.setOnClickListener(noSessionOnClickListener);
                runButton.setImageDrawable(getDrawable(R.drawable.disconnected));
                runButton.animate().cancel();
                runButton.clearAnimation();
                infoTextView.setTextColor(getColor(R.color.red));
                infoTextView.setText(getString(R.string.disconnected_info_text));
        }
    }

    private void onConsoleButtonClicked(View v) {
        String command = consoleEditText.getText().toString();
        consoleEditText.setText("");
        AppContext.instance().runSessionManager.session().sendMessage(Message.of(Action.INPUT, command));
    }

    private void onConsoleTextViewClicked(View v) {
        this.consoleEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(consoleEditText, InputMethodManager.SHOW_IMPLICIT);
    }

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


}