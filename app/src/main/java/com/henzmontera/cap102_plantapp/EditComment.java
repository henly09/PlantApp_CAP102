package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class EditComment extends AppCompatActivity {

    private ImageFilterButton BackButton;
    private CircleImageView ImageUserProfile;
    private EditText EditTextView;
    private  Button CancelButton;
    private  Button SaveButton;
    private SessionManager sessionManager;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin(); //Check if Logged in
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(R.anim.slide_in_right,androidx.navigation.ui.R.anim.nav_default_pop_exit_anim); // Transition during Opening this Activity
        BackButton = findViewById(R.id.EditComment_BackButton);
        ImageUserProfile = findViewById(R.id.EditComment_ImageProfilePost);
        EditTextView = findViewById(R.id.EditComment_EditTextDescription);
        CancelButton = findViewById(R.id.EditComment_CancelButton);
        SaveButton = findViewById(R.id.EditComment_SaveButton);

        CancelButton.setBackgroundColor(getColor(R.color.grayed2_color));
        CancelButton.setTextColor(getColor(R.color.black));

        Intent getintent = getIntent();
        String comment_text = getintent.getStringExtra("comment_text");

        //Retrieve User's Name and Print on TextView
        HashMap<String, String> user = sessionManager.getUserDetail();
        String image = user.get(sessionManager.UIMAGE); //Get Image String from Session manager

        EditTextView.setText(comment_text);
        if(image.isEmpty()){ // If image no string, default profile picture.
            ImageUserProfile.setBackgroundResource(R.mipmap.ic_nature_foreground);
        } else { // Else, set Image
            ImageUserProfile.setImageBitmap(StringtoImage(image));
        }

        if(EditTextView.getText().toString().equals(comment_text)){
            SaveButton.setEnabled(false);
            SaveButton.setBackgroundColor(getColor(R.color.InActiveAAAFB4));
            SaveButton.setTextColor(getColor(R.color.grayed1_color));
        }

        EditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(EditTextView.getText().toString().equals(comment_text)){
                    SaveButton.setEnabled(false);
                    SaveButton.setBackgroundColor(getColor(R.color.InActiveAAAFB4));
                    SaveButton.setTextColor(getColor(R.color.grayed1_color));
                } else {
                    SaveButton.setEnabled(true);
                    SaveButton.setBackgroundColor(getColor(R.color.grayed2_color));
                    SaveButton.setTextColor(getColor(R.color.black));
                }
            }
        });

        CancelButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });

        BackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });

        SaveButton.setOnClickListener(view -> {
            if(SaveButton.isEnabled()){
                String commentid = getintent.getStringExtra("commentid");
                String postid = getintent.getStringExtra("postid");
                String commenter_userid = getintent.getStringExtra("commenter_userid");
                String comment_editText = EditTextView.getText().toString();

                SaveComment(commentid, postid, commenter_userid, comment_editText);
            }
        });
    }

    private void SaveComment(String commentid, String postid, String commenter_userid, String comment_text){
        String url = getString(R.string.EditComment);
        RequestQueue q = Volley.newRequestQueue(EditComment.this);
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
                }, error -> Toast.makeText(EditComment.this, "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("commentid", commentid);
                param.put("postid", postid);
                param.put("commenter_userid", commenter_userid);
                param.put("comment_text", comment_text);
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
        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim, R.anim.slide_out_right);
    }
}