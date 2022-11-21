package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ortiz.touchview.TouchImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewingPostImageActivity extends AppCompatActivity {

    private TouchImageView ImageSelected;
    private ImageFilterButton BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_post_image);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim, androidx.navigation.ui.R.anim.nav_default_pop_exit_anim); // Transition during Opening this Activity
        ImageSelected = findViewById(R.id.ViewPostImage_ImageSelected);
        BackButton = findViewById(R.id.ViewPostImage_BackButton);
        Intent getintent = getIntent();
        String postid = getintent.getStringExtra("postid");
        GetPostImage(postid);

        BackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private void GetPostImage(String postid){
        String url = getString(R.string.GetUserPost);

        RequestQueue q = Volley.newRequestQueue(ViewingPostImageActivity.this);

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray userpost = oh.getJSONArray("CurrentPost");
                        JSONObject al = userpost.getJSONObject(0);
                        ImageSelected.setImageBitmap(StringtoImage(al.optString("postImage")));
                    } catch (Exception e) {
                    }
                }, error -> {
            Toast.makeText(ViewingPostImageActivity.this, "Fetching Data From Database Failed. Please Try Again Later..", Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("postid", postid);
                return param;
            }
        };
        q.add(r);
    }

    //Convert from String to Bitmap Image
    private Bitmap StringtoImage(String string) {
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim, androidx.navigation.ui.R.anim.nav_default_pop_exit_anim);
    }
}