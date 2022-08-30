package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UserEditText;
    EditText PasswordEditText;
    TextView RegisText;

    String[] userArray = new String[1];
    String[] passArray = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = findViewById(R.id.LoginButton);
        UserEditText = findViewById(R.id.editLoginUsernameText);
        PasswordEditText = findViewById(R.id.editLoginPasswordText);
        RegisText = findViewById(R.id.RegisterText);

        String user = UserEditText.getText().toString();
        String pass = PasswordEditText.getText().toString();

        /////////////////////////////////////////////////////////////////////////////

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RegisText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}