package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
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

      //  MicrosoftDataImport(); Ayaw hilabti <-- Henz

        LoginButton = findViewById(R.id.LoginButton);
        UserEditText = findViewById(R.id.editLoginUsernameText);
        PasswordEditText = findViewById(R.id.editLoginPasswordText);
        RegisText = findViewById(R.id.RegisterText);

        String user = UserEditText.getText().toString();
        String pass = PasswordEditText.getText().toString();

        /////////////////////////////////////////////////////////////////////////////

        RegisText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //////////////////////////////////////////////////////////////////////////////

        LoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void MicrosoftDataImport(){ // <---- Ayaw hilabti - Henz
        MicrosoftDataImporter Mdataimporter = new MicrosoftDataImporter();
        Mdataimporter.FirebaseTest();
    }

}