package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static java.sql.Types.NULL;

public class AddPostActivity extends AppCompatActivity {

    ImageView ProfilePicThread;
    TextView PostText;
    TextView UserTextView;
    EditText EditTextDescriptionWriteMessage;
    Button AddImageButtonThread;
    ImageView backButtonPost;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ProfilePicThread = findViewById(R.id.ImageProfilePost);
        UserTextView = findViewById(R.id.userPostTextView);
        PostText = findViewById(R.id.ButtonPostText);
        EditTextDescriptionWriteMessage = findViewById(R.id.EditTextDescriptionMessagesInput);
        AddImageButtonThread = findViewById(R.id.AddImageButton);
        backButtonPost = findViewById(R.id.backbuttonpost);

        //Retrieve User's Name and Print in TextView
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String username = user.get(sessionManager.UNAME);
        UserTextView.setText(username);

        PostText.setOnClickListener(view ->{
            //Retrieve User's Id
            String id = user.get(sessionManager.UID);

            //Constructor Get Current Time
            Date currentTime = Calendar.getInstance().getTime();

            //Get Description and Images
            String description = EditTextDescriptionWriteMessage.getText().toString();
            int image = NULL;

            PostMethod(id, image, description, currentTime.toString());
        });

        AddImageButtonThread.setOnClickListener(view ->{
            //Planning how to use BITMAP
        });

        backButtonPost.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private void PostMethod(String UserId, int Image, String Desc, String DateTime){
        String url = "http://192.168.254.100/networkingbased/AddPost.php";

        RequestQueue q = Volley.newRequestQueue(AddPostActivity.this);

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try{
                        Toast.makeText(AddPostActivity.this, "Your post was shared", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPostActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    catch(Exception e){

                    }
                }, error -> Toast.makeText(AddPostActivity.this, "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("POSTUSERID", UserId);
                param.put("POSTIMAGE", String.valueOf(Image));
                param.put("POSTDESC", Desc);
                param.put("POSTDATETIME", DateTime);
                return param;
            }
        };
        q.add(r);
    }


}