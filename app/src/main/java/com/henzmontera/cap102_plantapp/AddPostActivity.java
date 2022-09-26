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
import android.util.Base64;
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
    private MultiImageView SelectedImages;
    private TextView PostText;
    private TextView UserTextView;
    private EditText EditTextDescriptionWriteMessage;
    private Button AddImageButtonThread;
    private ImageView backButtonPost;
    private SessionManager sessionManager;

    private static final int CAMERA_REQUEST = 1009;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ProfilePicThread = findViewById(R.id.ImageProfilePost);
        SelectedImages = findViewById(R.id.pimagetv);
        UserTextView = findViewById(R.id.userPostTextView);
        PostText = findViewById(R.id.ButtonPostText);
        EditTextDescriptionWriteMessage = findViewById(R.id.EditTextDescriptionMessagesInput);
        AddImageButtonThread = findViewById(R.id.AddImageButton);
        backButtonPost = findViewById(R.id.backbuttonpost);

        //Retrieve User's Name and Print in TextView
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String username = user.get(sessionManager.UNAME);
        String img = user.get(sessionManager.UIMAGE);
        UserTextView.setText(username);

        //Decode from String into Bitmap
        byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ProfilePicThread.setImageBitmap(decodedByte);

        PostText.setOnClickListener(view ->{
            String id = user.get(sessionManager.UID);//Retrieve User's Id
            String description = EditTextDescriptionWriteMessage.getText().toString();//Get Description and Images
            String imageString = imageToString(bitmap);
            Posting(id, description, imageString);
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
            } catch (Exception e){

            }
        }
    }

    private void Posting(String id , String desc, String image){
        String url = "http://192.168.254.107/networkingbased/AddPost.php";
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
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}