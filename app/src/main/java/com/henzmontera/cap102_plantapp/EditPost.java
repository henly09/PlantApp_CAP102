package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditPost extends AppCompatActivity {

    private ImageFilterButton BackButton;
    private TextView SaveButton;
    private TextView UserName;
    private ImageView UserImage;
    private EditText EditTextDescription;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(R.anim.slide_up, androidx.navigation.ui.R.anim.nav_default_pop_exit_anim); // Transition during Opening this Activity
        BackButton = findViewById(R.id.EditComment_BackButton);
        SaveButton = findViewById(R.id.EditPost_SaveTextView);
        UserName = findViewById(R.id.EditPost_UserNickname);
        UserImage = findViewById(R.id.EditComment_ImageProfilePost);
        EditTextDescription = findViewById(R.id.EditComment_EditTextDescription);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String username = user.get(sessionManager.UNAME);
        String img = user.get(sessionManager.UIMAGE);
        UserName.setText(username);

        if(img.isEmpty()){ // If image no string, default profile picture.
            UserImage.setBackgroundResource(R.mipmap.ic_nature_foreground);
        } else { // Else, set Image
            UserImage.setImageBitmap(StringtoImage(img));
        }

        //Get Intent
        Intent intent = getIntent();
        String description = intent.getStringExtra("description");

        //Set Value from Intent
        EditTextDescription.setText(description);

        //On Change EditText Setup
        EditTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(EditTextDescription.getText().toString().equals(description)){
                    SaveButton.setTextIsSelectable(false);
                    SaveButton.setTextColor(getColor(R.color.gray));
                } else {
                    SaveButton.setTextIsSelectable(true);
                    SaveButton.setTextColor(getColor(R.color.paleblue));
                }
            }
        });

        BackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });

        SaveButton.setOnClickListener(view -> {
            if(SaveButton.isTextSelectable()){
                String userid = user.get(sessionManager.UID);
                String postid = intent.getStringExtra("postid");
                String editeddescription = EditTextDescription.getText().toString();

                SaveEditPost(userid, postid, editeddescription);
            }
        });
    }

    private void SaveEditPost(String userid, String postid, String description){
        String url = getString(R.string.EditPost);
        RequestQueue q = Volley.newRequestQueue(EditPost.this);
        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try{
                        onBackPressed();
                        finish();
                    }
                    catch(Exception e){
                    }
                }, error -> Toast.makeText(EditPost.this, "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("userid", userid);
                param.put("postid", postid);
                param.put("description", description);
                return param;
            }
        };
        q.add(r);
    }

    //Convert from String to Bitmap Image
    private Bitmap StringtoImage(String string){
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim, R.anim.slide_down);
    }
}