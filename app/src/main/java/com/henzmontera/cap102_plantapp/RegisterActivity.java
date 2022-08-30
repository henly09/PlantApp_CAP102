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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

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

        //Get the Texts and Convert into String type
        String email = EditEmail.getText().toString();
        String user = EditUser.getText().toString();
        String pass = EditPass.getText().toString();

        ////////////////////////////////////////////////////////////////////

        //Going back to Login Activity
        LoginText.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ////////////////////////////////////////////////////////////////////

        //Register New User
        RegisterButton.setOnClickListener(view -> {
            String url = "http://172.21.48.1/networkingbased/RegisterUser.php";
            RequestQueue request = Volley.newRequestQueue(view.getContext());
            JsonArrayRequest RRequest = new JsonArrayRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(view.getContext(), "Failed to login.\n In ErrorListener \n" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("EMAIL", email);
                    param.put("USER", user);
                    param.put("PASS", pass);
                    return param;
                }
            };
            request.add(RRequest);
        });
    }
}