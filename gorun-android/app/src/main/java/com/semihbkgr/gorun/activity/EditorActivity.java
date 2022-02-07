package com.semihbkgr.gorun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.code.Code;
import com.semihbkgr.gorun.dialog.AppDialog;
import com.semihbkgr.gorun.dialog.CodeListDialog;
import com.semihbkgr.gorun.dialog.CodeSaveDialog;
import com.semihbkgr.gorun.highlight.CodeHighlighter;
import com.semihbkgr.gorun.highlight.DefaultCodeHighlighter;
import com.semihbkgr.gorun.message.Action;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.run.RunSessionObserver;
import com.semihbkgr.gorun.run.RunSessionStatus;
import com.semihbkgr.gorun.util.ActivityUtils;
import com.semihbkgr.gorun.util.TextWatcherAdapter;
import com.semihbkgr.gorun.view.CodeEditorView;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = EditorActivity.class.getName();

    // UI Components
    private CodeEditorView codeEditorView;
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
    private TextView infoTextView;
    private TextView codeTitleTextView;
    private ImageButton codeSaveImageButton;
    private AppDialog codeSaveDialog;
    private AppDialog codeListDialog;

    // Common used toast
    private Toast toast;

    private Code loadedCode;
    private RunSessionObserver runSessionObserver;
    private CodeHighlighter codeHighlighter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ActivityUtils.loadActionBar(this, "GoRun");

        // Find and assign UI components
        this.codeEditorView = findViewById(R.id.codeEditText);
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
        this.infoTextView = findViewById(R.id.infoTextView);
        this.codeTitleTextView = findViewById(R.id.codeTitleTextView);
        this.codeSaveImageButton = findViewById(R.id.codeSaveImageButton);

        // Set UI Components' listeners
        consoleButton.setOnClickListener(this::onConsoleButtonClicked);
        consoleTextView.setOnClickListener(this::onConsoleTextViewClicked);
        leftBraceButton.setOnClickListener(this::onLeftBraceButtonClicked);
        rightBraceButton.setOnClickListener(this::onRightBraceButtonClicked);
        leftCurlyBraceButton.setOnClickListener(this::onLeftCurlyBraceButtonClicked);
        rightCurlyBraceButton.setOnClickListener(this::onRightCurlyBraceButtonClicked);
        quoteButton.setOnClickListener(this::onQuoteButtonClicked);
        tabButton.setOnClickListener(this::onTabButtonClicked);
        codeSaveImageButton.setOnClickListener(this::onCodeSaveImageButtonClicked);


        this.codeHighlighter = new DefaultCodeHighlighter(codeEditorView, AppConstants.Highlights.HIGHLIGHT_UNIT_LIST);
        codeEditorView.addTextChangedListener((TextWatcherAdapter) (s, start, before, count) ->
                AppContext.execute(codeHighlighter::highlight));
        AppContext.execute(codeHighlighter::highlight);

        this.toast = Toast.makeText(this, getString(R.string.connecting_toast_message), Toast.LENGTH_SHORT);

        this.codeSaveDialog = new CodeSaveDialog(this, R.style.Theme_AppCompat_Dialog,
                AppContext.instance().codeService, AppContext.instance().executorService);
        this.codeListDialog = new CodeListDialog(this, R.style.Theme_AppCompat_Dialog,
                AppContext.instance().codeService, AppContext.instance().executorService,
                code -> {
                    this.loadedCode = code;
                    codeEditorView.setText(code.getContent());
                    codeTitleTextView.setVisibility(View.VISIBLE);
                    codeTitleTextView.setText(code.getTitle());
                    codeSaveImageButton.setVisibility(View.VISIBLE);
                    codeSaveImageButton.setClickable(true);
                });

        this.runSessionObserver = status -> {
            Log.i(TAG, "RunSessionObserver, status: " + status.name());
            runOnUiThread(() -> onRunSessionStatusChanged(status));
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AppContext.instance().runSessionManager.getStatus() == RunSessionStatus.NO_SESSION)
            AppContext.instance().runSessionManager.connect();
        String code = getIntent().getStringExtra(AppConstants.Values.INTENT_SNIPPET_CODE_NAME);
        if (code != null) codeEditorView.setText(code);
        AppContext.instance().runSessionManager.registerObserver(runSessionObserver);
        onRunSessionStatusChanged(AppContext.instance().runSessionManager.getStatus());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.instance().runSessionManager.unregisterObserver(runSessionObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingItem) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.addItem) {
            codeSaveDialog.addProperty(AppConstants.Values.DIALOG_PROPERTY_CODE_CONTENT, codeEditorView.getText().toString());
            codeSaveDialog.show();
        } else if (item.getItemId() == R.id.listItem) {
            codeListDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Updates ui and listeners regarding given RunSessionStatus
    private void onRunSessionStatusChanged(RunSessionStatus status) {
        switch (status) {
            case HAS_SESSION:
                runButton.setOnClickListener(v -> {
                    String code = codeEditorView.getText().toString();
                    AppContext.instance().runSessionManager.session().sendMessage(Message.of(Action.RUN, code));
                    consoleTextView.setText("");
                });
                runButton.setImageDrawable(getDrawable(R.drawable.run));
                runButton.animate().cancel();
                runButton.clearAnimation();
                infoTextView.setTextColor(getColor(R.color.green));
                infoTextView.setText(getString(R.string.connected_info_text));
                AppContext.instance().runSessionManager.session().addMessageConsumer(message -> {
                    Log.i(TAG, "Message action: " + message.action.name() + ", body: " + message.body);
                    runOnUiThread(() -> onMessageReceived(message));
                });
                break;
            case CREATING:
                runButton.setOnClickListener(v -> {
                    // Show 'connecting' toast massage if it is not showing at that time.
                    try {
                        if (!toast.getView().isShown()) {
                            toast.show();
                        }
                    } catch (Exception e) {
                        this.toast = Toast.makeText(this, getString(R.string.connecting_toast_message), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                runButton.setImageDrawable(getDrawable(R.drawable.connecting));
                runButton.clearAnimation();
                runButton.animate().rotationBy(1800).setDuration(10000);
                infoTextView.setTextColor(getColor(R.color.black));
                infoTextView.setText(getString(R.string.connecting_info_text));
                break;
            case NO_SESSION:
                runButton.setOnClickListener(v -> {
                    AppContext.instance().runSessionManager.connect();
                });
                runButton.setImageDrawable(getDrawable(R.drawable.disconnected));
                runButton.animate().cancel();
                runButton.clearAnimation();
                infoTextView.setTextColor(getColor(R.color.red));
                infoTextView.setText(getString(R.string.disconnected_info_text));
        }
    }

    private void onMessageReceived(Message message) {
        switch (message.action) {
            case RUN_ACK:
                consoleTextView.setText("go run main.go : " + message.body + "\n");
                return;
            case OUTPUT:
                consoleTextView.setText(consoleTextView.getText() + message.body);
                return;
            case COMPLETED:
                consoleTextView.setText(consoleTextView.getText() + "Completed, executionTimeMs: " + message.body);
                return;
            default:
                Log.w(TAG, "onMessageReceived: uncovered message, action: " + message.action.name());
        }
    }

    // Listener function
    private void onConsoleButtonClicked(View v) {
        String command = consoleEditText.getText().toString();
        consoleEditText.setText("");
        AppContext.instance().runSessionManager.session().sendMessage(Message.of(Action.INPUT, command));
    }

    // Listener function
    private void onConsoleTextViewClicked(View v) {
        this.consoleEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(consoleEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    // Listener function
    private void onCodeSaveImageButtonClicked(View v) {
        if (loadedCode == null) {
            return;
        }
        loadedCode.setContent(codeEditorView.getText().toString());
        AppContext.instance().executorService.execute(() -> {
            AppContext.instance().codeService.save(null);
        });

    }

    // Listener function
    private void onLeftBraceButtonClicked(View view) {
        Log.v(TAG, "onLeftBraceButtonClicked: button has been clicked");
        codeEditorView.addText("(");
    }

    // Listener function
    private void onRightBraceButtonClicked(View view) {
        Log.v(TAG, "onRightBraceButtonClicked: button has been clicked");
        codeEditorView.addText(")");
    }

    // Listener function
    private void onLeftCurlyBraceButtonClicked(View view) {
        Log.v(TAG, "onLeftCurlyBraceButtonClicked: button has been clicked");
        codeEditorView.addText("{");
    }

    // Listener function
    private void onRightCurlyBraceButtonClicked(View view) {
        Log.v(TAG, "onRightCurlyBraceButtonClicked: button has been clicked");
        codeEditorView.addText("}");
    }

    // Listener function
    private void onQuoteButtonClicked(View view) {
        Log.v(TAG, "onQuoteButtonClicked: button has been clicked");
        codeEditorView.quote();
    }

    // Listener function
    private void onTabButtonClicked(View view) {
        Log.v(TAG, "onTabButtonClicked: button has been clicked");
        codeEditorView.addText("\t");
    }

}