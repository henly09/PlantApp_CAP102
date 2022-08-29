package com.henzmontera.cap102_plantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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


               RequestQueue request = Volley.newRequestQueue(view.getContext());
               JsonArrayRequest RRequest = new JsonArrayRequest(
                       Request.Method.POST,
                       "http://172.21.48.1/networkingbased/LoginUser.php",
                       null,
                       new Response.Listener<JSONArray>() {
                           @Override
                           public void onResponse(JSONArray response) {

                               try {
                                   for (int a = 0; a < response.length(); a++) {
                                       JSONObject object = response.getJSONObject(a);
                                       userArray[a] = object.getString("username");
                                       passArray[a] = object.getString("userpassword");
                                   }
                               } catch (JSONException e) {
                                   Toast.makeText(view.getContext(), "Failed to login." + e.getMessage(), Toast.LENGTH_LONG).show();
                               }


                               for (int a = 0; a < userArray.length ; a++){
                                   if(UserEditText.getText().toString().equals(userArray[a]) && PasswordEditText.getText().toString().equals(passArray[a])){
                                       Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                   }
                                   else{
                                       Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                   }
                               }

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
                       param.put("username", user);
                       param.put("userpassword", pass);
                       return param;
                   }
               };
               request.add(RRequest);
           }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////

        RegisText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
           }
        });

    }
}