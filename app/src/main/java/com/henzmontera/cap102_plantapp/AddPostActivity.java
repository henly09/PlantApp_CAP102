package com.henzmontera.cap102_plantapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageView ProfilePicThread;
    private Button PostButton;
    private TextView UserTextView;
    private EditText EditTextDescriptionWriteMessage;
    private ImageView backButtonPost;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(R.anim.slide_in_left, androidx.navigation.ui.R.anim.nav_default_pop_exit_anim); // Transition during Opening this Activity
        ProfilePicThread = findViewById(R.id.ImageProfilePost);
        UserTextView = findViewById(R.id.userPostTextView);
        PostButton = findViewById(R.id.ButtonPostText);
        EditTextDescriptionWriteMessage = findViewById(R.id.EditTextDescriptionMessagesInput);
        backButtonPost = findViewById(R.id.backbuttonpost);

        //Retrieve User's Name and Print in TextView
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String username = user.get(sessionManager.UNAME);
        String img = user.get(sessionManager.UIMAGE);
        UserTextView.setText(username);

        if(img.isEmpty()){ // If image no string, default profile picture.
            ProfilePicThread.setBackgroundResource(R.mipmap.ic_nature_foreground);
        } else { // Else, set Image
            ProfilePicThread.setImageBitmap(StringtoImage(img));
        }

        if(EditTextDescriptionWriteMessage.getText().toString().isEmpty()){
            PostButton.setEnabled(false);
            PostButton.setTextColor(getColor(R.color.TextInactive));
            PostButton.setBackgroundColor(getColor(R.color.IfNoValue));
        }

        EditTextDescriptionWriteMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { // On/When Changed
                if(EditTextDescriptionWriteMessage.getText().toString().isEmpty()){
                    PostButton.setEnabled(false);
                    PostButton.setTextColor(getColor(R.color.TextInactive));
                    PostButton.setBackgroundColor(getColor(R.color.IfNoValue));
                } else{
                    PostButton.setEnabled(true);
                    PostButton.setTextColor(getColor(R.color.black));
                    PostButton.setBackgroundColor(getColor(R.color.IfHasValue));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (PostButton.isEnabled()){ // If Enabled
                    PostButton.setOnClickListener(view ->{
                        if(EditTextDescriptionWriteMessage.getText().toString().isEmpty()){ // Description and Picture
                            return;
                        }
                        String id = user.get(sessionManager.UID);//Retrieve User's Id
                        String description = EditTextDescriptionWriteMessage.getText().toString();//Get Description
                        Posting(id, description);
                    });
                }
            }
        });

        backButtonPost.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private void Posting(String id , String desc){
        String url = getString(R.string.AddPost);
        RequestQueue q = Volley.newRequestQueue(AddPostActivity.this);
        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try{
                        Toast.makeText(AddPostActivity.this, "Your post was shared", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }
                    catch(Exception e){
                    }
                }, error -> Toast.makeText(AddPostActivity.this, "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("POSTUSERID", id);
                param.put("POSTDESC", desc);
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
        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim, R.anim.slide_out_left);
    }
}