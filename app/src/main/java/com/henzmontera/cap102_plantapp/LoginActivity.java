package com.henzmontera.cap102_plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UserEditText;
    EditText PasswordEditText;

    String inputUser = "";
    String inputPass = "";
    String[] userArray = new String[1];
    String[] passArray = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = findViewById(R.id.LoginButton);
        UserEditText = findViewById(R.id.editLoginUsernameText);
        PasswordEditText = findViewById(R.id.editLoginPasswordText);
        LoginButton.setOnClickListener(clickLogin);
    }

    View.OnClickListener clickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RequestQueue request = Volley.newRequestQueue(view.getContext());
            JsonArrayRequest RRequest = new JsonArrayRequest(
                    Request.Method.POST,
                    "http://172.20.80.1/LoginUser.php",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                for (int a = 0; a < response.length(); a++) {
                                    JSONObject object = response.getJSONObject(a);
                                    userArray[a] = object.getString("USER");
                                    passArray[a] = object.getString("PASS");
                                }
                            } catch (JSONException e) {
                                Toast.makeText(view.getContext(), "Failed to login." + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            inputUser = UserEditText.getText().toString();
                            inputPass = PasswordEditText.getText().toString();

                            if(TextUtils.isEmpty(UserEditText.getText().toString()))

                            if(inputUser.equals(userArray[0]) && inputPass.equals(passArray[0])){
                                Toast.makeText(view.getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else if(inputUser.equals(userArray[1]) && inputPass.equals(passArray[1])){
                                Toast.makeText(view.getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            }else if(inputUser.equals(userArray[2]) && inputPass.equals(passArray[2])){
                                Toast.makeText(view.getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(view.getContext(), "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(view.getContext(), "Failed to login.\n In ErrorListener" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
            );
            request.add(RRequest);
        }
    };
}