package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        EditUser = findViewById(R.id.editRegisterUsername);
        EditPass = findViewById(R.id.editRegisterPassword);
        RegisterButton = findViewById(R.id.RegisterButton);
        LoginText = findViewById(R.id.LoginText);

        ////////////////////////////////////////////////////////////////////

        //Going back to Login Activity
        LoginText.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ////////////////////////////////////////////////////////////////////

        //Register New User
        RegisterButton.setOnClickListener(view -> {
            String url = "http://192.168.254.100/networkingbased/RegisterUser.php";
            RequestQueue request = Volley.newRequestQueue(RegisterActivity.this);
            StringRequest RRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                Toast.makeText(RegisterActivity.this, "User Create Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }catch(Exception e){
                                Toast.makeText(RegisterActivity.this, "String Exception Error!!\n\n" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "Volley Error.\nIn ErrorListener\n" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
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
        });
    }
}