package com.henzmontera.cap102_plantapp;

import static java.sql.Types.NULL;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView UsernameProfile;
    private TextView emptyView;
    private ImageView UserPictureProfile;

    private FloatingActionButton BackButtonProfile;
    private FloatingActionButton AddButtonThread;

    private SwipeRefreshLayout swiperefresh;
    private PostAdapter useradapt;
    private RecyclerView recyclerview;
    private List<ListPost> listposts;

    private Bitmap bitmap;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(R.anim.slide_in_left, androidx.navigation.ui.R.anim.nav_default_pop_exit_anim); // Transition during Opening this Activity
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin(); //Check if Logged in

        //FAB Button
        BackButtonProfile = findViewById(R.id.BackButtonProfile);
        AddButtonThread = findViewById(R.id.AddAddButton);

        //This Textview display if empty data list
        emptyView = findViewById(R.id.TextviewEmpty);

        //Textview's Username
        UsernameProfile = findViewById(R.id.TextviewUsernameProfile);

        //Images
        UserPictureProfile = findViewById(R.id.ImageProfilePost);

        //RecyclerView
        recyclerview = findViewById(R.id.recyclerVV);

        //RecyclerView Layout
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        listposts = new ArrayList<>();

        //Retrieve User's Name and Print on TextView
        HashMap<String, String> user = sessionManager.getUserDetail();
        String username = user.get(sessionManager.UNAME); //Get Username String from Session Manager
        String id = user.get(sessionManager.UID);   //Get Id String from Session Manager
        String image = user.get(sessionManager.UIMAGE); //Get Image String from Session manager
        UsernameProfile.setText(username);

        if(image.isEmpty()){ // If image no string, default profile picture.
            UserPictureProfile.setBackgroundResource(R.mipmap.ic_nature_foreground);
        } else { // Else, set Image
            UserPictureProfile.setImageBitmap(StringtoImage(image));
        }

        //Call Display Post Method
        DisplayPost(id);

        //SwipeRefresh function
        swiperefresh = findViewById(R.id.LowerProfileConstraintLayout);
        swiperefresh.setOnRefreshListener(() -> {
            listposts.clear();
            DisplayPost(id);
            swiperefresh.setRefreshing(false); //False to Animation
        });

        //BackButton w/ Finish
        BackButtonProfile.setOnClickListener(view ->{
            onBackPressed();
            finish();
        });

        //Add Post
        AddButtonThread.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, AddPostActivity.class);
            startActivity(intent);
        });



        //Select Profile Picture
        UserPictureProfile.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("userId", id);

            AddPhotoBottomDialogFragment addPhotoBottomDialogFragment = AddPhotoBottomDialogFragment.newInstance();
            addPhotoBottomDialogFragment.setArguments(bundle);
            addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                    "add_photo_dialog_fragment");
        });
    }

    private void DisplayPost(String id){ // Display the Results of loaded datasets
        String url = getString(R.string.DisplayProfilePost);

        RequestQueue q = Volley.newRequestQueue(ProfileActivity.this);

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray userpost = oh.getJSONArray("UserPosts");

                        if (userpost.length() == NULL) {
                            emptyView.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);
                            listposts.clear();
                            for (int i = 0; i < userpost.length(); i++) {
                                JSONObject al = userpost.getJSONObject(i);

                                ListPost post = new ListPost(
                                        al.optString("postId"),
                                        al.optString("postUserId"),
                                        al.optString("username"),
                                        al.optString("userprofilepicture"),
                                        al.optString("postDescriptions"),
                                        al.optString("postImage"),
                                        al.optString("postTime"),
                                        al.optString("commentCount"),
                                        al.optString("likeCount")
                                );
                                listposts.add(post);
                                useradapt = new PostAdapter(ProfileActivity.this, listposts);
                                recyclerview.setAdapter(useradapt);
                                useradapt.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                    }
                }, error -> {
            Toast.makeText(ProfileActivity.this, "Fetching Data From Database Failed. Please Try Again Later..", Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("userid", id);
                return param;
            }
        };
        q.add(r);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = getContentResolver();
           try {
               Uri selectedImage = data.getData();
               ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, selectedImage);
               bitmap = ImageDecoder.decodeBitmap(source);
               int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
               bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
               UserPictureProfile.setImageBitmap(bitmap);
               //Get User Id and send to method's parameter
               HashMap<String, String> user = sessionManager.getUserDetail();
               String id = user.get(sessionManager.UID);
               saveProfilePicture(id);
           } catch (Exception e) {
           }
    }

    private void saveProfilePicture(String Userid){
        String StoreURL = getString(R.string.SaveProfilePicture);
        RequestQueue q = Volley.newRequestQueue(ProfileActivity.this);
        StringRequest r = new StringRequest(
                Request.Method.POST,
                StoreURL,
                response -> {
                    try {
                        Toast.makeText(ProfileActivity.this, "Profile Picture has been updated", Toast.LENGTH_SHORT).show();
                        sessionManager.updateUserProfilePicture(imageToString(bitmap)); //Update Profile Picture on Session Manager
                    } catch (Exception e) {
                    }
                }, error -> {

                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("userid", String.valueOf(Userid));
                param.put("image", imageToString(bitmap));
                return param;
            }
        };
        q.add(r);
    }

    //Convert image into String
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
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
