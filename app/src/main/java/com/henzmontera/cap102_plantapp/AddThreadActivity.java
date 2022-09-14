package com.henzmontera.cap102_plantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddThreadActivity extends AppCompatActivity {

    ImageView ProfilePicThread;
    TextView PostText;
    EditText EditTextThreadWriteMessage;
    Button AddImageButtonThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ProfilePicThread = findViewById(R.id.ImageProfilePost);
        PostText = findViewById(R.id.ButtonPostText);
        EditTextThreadWriteMessage = findViewById(R.id.EditTextDescriptionMessagesInput);
        AddImageButtonThread = findViewById(R.id.AddImageButton);

        String thread = EditTextThreadWriteMessage.getText().toString();

        PostText.setOnClickListener(view ->{

        });

        AddImageButtonThread.setOnClickListener(view ->{

        });

    }


}