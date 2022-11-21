package com.henzmontera.cap102_plantapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stfalcon.multiimageview.MultiImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageView ProfilePicThread;
    private Button PostButton;
    private TextView UserTextView;
    private EditText EditTextDescriptionWriteMessage;
    private ImageView backButtonPost;
    private SessionManager sessionManager;

    private Button AddImageButtonThread;
    private MultiImageView SelectedImages;

    private static final int CAMERA_REQUEST = 1009;
    private Bitmap bitmap;

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

        AddImageButtonThread = findViewById(R.id.AddImageButton);
        SelectedImages = findViewById(R.id.pimagetv);

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
                        if(SelectedImages != null && !EditTextDescriptionWriteMessage.getText().toString().isEmpty()){ // Description and Picture
                            String id = user.get(sessionManager.UID);//Retrieve User's Id
                            String description = EditTextDescriptionWriteMessage.getText().toString();//Get Description
                            String imageString = imageToString(bitmap); // Get Image
                            Posting(id, description, imageString);
                        }
                        if(SelectedImages == null){ //Only Description
                            String id = user.get(sessionManager.UID);
                            String description = EditTextDescriptionWriteMessage.getText().toString();
                            String imageString = "";
                            Posting(id, description, imageString);
                        }
                    });
                }
            }
        });

        AddImageButtonThread.setOnClickListener(view ->{
            if(ContextCompat.checkSelfPermission(AddPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(AddPostActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            if(ContextCompat.checkSelfPermission(AddPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

        backButtonPost.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = getContentResolver();
        if(requestCode == CAMERA_REQUEST & resultCode == RESULT_OK){
            try{
                Uri selectedImage = data.getData();
                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, selectedImage);
                bitmap = ImageDecoder.decodeBitmap(source);
                int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                SelectedImages.setImageBitmap(bitmap);
                Log.d("ImageString", imageToString(bitmap));
            } catch (Exception e){

            }
        }
    }

    private void Posting(String id , String desc, String image){
        String url = getString(R.string.AddPost);
        RequestQueue q = Volley.newRequestQueue(AddPostActivity.this);
        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try{
                        Toast.makeText(AddPostActivity.this, "Your post was shared", Toast.LENGTH_SHORT).show();
                        Log.d("imageString :", image+"");
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
                param.put("POSTIMAGE", image);
                param.put("POSTDESC", desc);
                return param;
            }
        };
        q.add(r);
    }

    //Convert image into String
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
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