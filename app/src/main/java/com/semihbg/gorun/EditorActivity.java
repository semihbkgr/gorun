package com.semihbg.gorun;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.semihbg.gorun.util.TextChangeUpdater;

public class EditorActivity extends AppCompatActivity {

    private Button runButton;
    private EditText codeEditText;
    private TextView outputTextView;
    private TextChangeUpdater textChangeUpdater;

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


        textChangeUpdater=new TextChangeUpdater(outputTextView);

        if(AppSocketSessionHolder.isConnected())
           AppSocketSessionHolder.getSession().addOutputConsumer(str -> {
               textChangeUpdater.append(str);
               System.out.println("----------------------------------"+str);
           });
        else Toast.makeText(this, "Cannot connect to server", Toast.LENGTH_SHORT).show();

    }


    private void onRunButtonClicked(View v){
        String code=codeEditText.getText().toString();
        textChangeUpdater.clear();
        AppSocketSessionHolder.getSession().run(code);
    }


}