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
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.R;
import com.semihbkgr.gorun.editor.CodeEditor;
import com.semihbkgr.gorun.message.Command;
import com.semihbkgr.gorun.message.Message;
import com.semihbkgr.gorun.util.view.TextChangeHandler;
import com.semihbkgr.gorun.util.view.TextChangeListener;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RunActivity extends AppCompatActivity {

    private static final String TAG = RunActivity.class.getName();
    private static final int CODE_EDITOR_UPDATE_DELAY_MS = 500;

    private CodeEditor codeEditor;
    private EditText consoleEditText;
    private Button runButton;
    private Button consoleButton;
    private TextView consoleTextView;
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
        setContentView(R.layout.activity_run);

        codeEditor = findViewById(R.id.codeEditText);
        consoleTextView = findViewById(R.id.outputTextView);
        consoleEditText = findViewById(R.id.inputEditText);
        consoleButton = findViewById(R.id.inputButton);
        runButton = findViewById(R.id.runButton);
        leftBraceButton = findViewById(R.id.leftBraceButton);
        rightBraceButton = findViewById(R.id.rightBraceButton);
        leftCurlyBraceButton = findViewById(R.id.leftCurlyBraceButton);
        rightCurlyBraceButton = findViewById(R.id.rightCurlyBraceButton);
        quoteButton = findViewById(R.id.quoteButton);
        tabButton = findViewById(R.id.tabButton);

        consoleButton.setOnClickListener(this::onConsoleButtonClicked);
        consoleTextView.setOnClickListener(this::onConsoleTextViewClicked);
        runButton.setOnClickListener(this::onRunButtonClicked);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        String code = getIntent().getStringExtra(AppConstants.Values.INTENT_EXTRA_SNIPPET_CODE);
        if (code != null) {
            Log.i(TAG, "onStart: Activity started with code");
            codeEditor.setText(code);
        } else
            Log.i(TAG, "onStart: Activity started with no code");
    }

    private void onRunButtonClicked(View v) {
        if (AppContext.instance().runWebSocketClient.hasSession()) {
            String code = codeEditor.getText().toString();
            consoleTextChangeHandler.clear();
            AppContext.instance().runWebSocketClient.session().sendMessage(Message.of(Command.RUN, code));
        } else {
            runButton.setText(R.string.connecting);
            AppContext.instance().executorService.execute(() -> {
                AppContext.instance().runWebSocketClient.connect(new WebSocketListener() {
                    @Override
                    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {

                    }

                    @Override
                    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {

                    }

                    @Override
                    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                        Log.e(TAG, "onCreate: Connection error");
                        runOnUiThread(() -> {
                            runButton.setText(R.string.connect);
                            Toast.makeText(RunActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {

                    }

                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {

                    }

                    @Override
                    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                        Log.e(TAG, "onCreate: Connection successful");
                        runOnUiThread(() -> {
                            runButton.setText(R.string.run);
                            Toast.makeText(RunActivity.this, "Connection successful", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
                AppContext.instance().runWebSocketClient.session().addMessageConsumer(message -> {
                    Log.i(TAG, "onCreate: Message : " + message.command + " - " + message.body);
                    if (message.command == Command.OUTPUT)
                        this.consoleTextChangeHandler.append(message.body);
                });
            });
        }

    }

    private void onConsoleButtonClicked(View v) {
        String command = consoleEditText.getText().toString();
        consoleEditText.setText("");
        AppContext.instance().executorService.execute(() -> AppContext.instance().runWebSocketClient.session().sendMessage(Message.of(Command.INPUT, command)));
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