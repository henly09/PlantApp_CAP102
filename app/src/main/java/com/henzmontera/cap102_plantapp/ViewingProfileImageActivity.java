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

public class ViewingProfileImageActivity extends AppCompatActivity {

    private ImageFilterButton BackButton;
    private TouchImageView ImageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_profile_image);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim, androidx.navigation.ui.R.anim.nav_default_pop_exit_anim); // Transition during Opening this Activity
        ImageSelected = findViewById(R.id.ViewImage_ImageSelected);
        BackButton = findViewById(R.id.ViewImage_BackButton);
        Intent getintent = getIntent();
        String userId = getintent.getStringExtra("userId");
        GetImage(userId);

        BackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private void GetImage(String userid){
        String url = getString(R.string.DisplayProfilePost);

        RequestQueue q = Volley.newRequestQueue(ViewingProfileImageActivity.this);

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray userpost = oh.getJSONArray("UserPosts");
                        JSONObject al = userpost.getJSONObject(0);
                        ImageSelected.setImageBitmap(StringtoImage(al.optString("userprofilepicture")));
                    } catch (Exception e) {
                    }
                }, error -> {
            Toast.makeText(ViewingProfileImageActivity.this, "Fetching Data From Database Failed. Please Try Again Later..", Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("userid", userid);
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