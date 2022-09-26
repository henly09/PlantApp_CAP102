package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText EditEmail;
    private EditText EditUser;
    private EditText EditPass;
    private EditText EditConfirmPass;
    private Button RegisterButton;
    private TextView LoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditEmail = findViewById(R.id.editRegisterEmail);
        EditUser = findViewById(R.id.editRegisterUsername);
        EditPass = findViewById(R.id.editRegisterPassword);
        EditConfirmPass = findViewById(R.id.editRegisterConfirmationPassword);
        RegisterButton = findViewById(R.id.RegisterButton);
        LoginText = findViewById(R.id.LoginText);

        ////////////////////////////////////////////////////////////////////

        //Going back to Login Activity
        LoginText.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        ////////////////////////////////////////////////////////////////////

        //Register New User
        RegisterButton.setOnClickListener(view -> {
            if(EditPass.getText().toString().equals(EditConfirmPass.getText().toString())){
                String url = "http://192.168.254.107/networkingbased/RegisterUser.php";
                RequestQueue request = Volley.newRequestQueue(RegisterActivity.this);
                StringRequest RRequest = new StringRequest(
                        Request.Method.POST,
                        url,
                        response -> {
                            try{
                                Toast.makeText(RegisterActivity.this, "User Create Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }catch(Exception e){
                                Toast.makeText(RegisterActivity.this, "String Exception Error!!\n\n" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Toast.makeText(RegisterActivity.this, "Volley Error.\nIn ErrorListener\n" + error.getMessage(), Toast.LENGTH_LONG).show()
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("EMAIL", EditEmail.getText().toString());
                        param.put("USER", EditUser.getText().toString());
                        param.put("PASS", EditPass.getText().toString());
                        return param;
                    }
                };
                request.add(RRequest);
            } else {
                Toast.makeText(this, "Those passwords didn’t match. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}