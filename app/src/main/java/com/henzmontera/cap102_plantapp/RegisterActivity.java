package com.henzmontera.cap102_plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText EditEmail;
    EditText EditUser;
    EditText EditPass;
    Button RegisterButton;
    TextView LoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditEmail = findViewById(R.id.editRegisterEmail);
        EditUser = findViewById(R.id.editLoginUsernameText);
        EditPass = findViewById(R.id.editLoginPasswordText);
        RegisterButton = findViewById(R.id.RegisterButton);
        LoginText = findViewById(R.id.LoginText);

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}