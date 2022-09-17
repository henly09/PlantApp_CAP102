package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class
ProfileActivity extends AppCompatActivity {

    private Button EditProfile; //I doubt to use this
    private TextView UsernameProfile;
    private TextView emptyView;
    private  ImageView UserBackgroundProfile;
    private ImageView UserPictureProfile;

    private FloatingActionButton BackButtonProfile;
    private FloatingActionButton AddButtonThread;

    private SwipeRefreshLayout swiperefresh;
    private PostAdapter useradapt;
    private RecyclerView recyclerview;
    private List<ListPost> listposts;

    SessionManager sessionManager;

    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        UserBackgroundProfile = findViewById(R.id.ImageProfileBackground);

        //RecyclerView
        recyclerview = findViewById(R.id.recyclerVV);

        //RecyclerView Layout
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        listposts = new ArrayList<>();

        //Retrieve User's Name and Print in TextView
        HashMap<String, String> user = sessionManager.getUserDetail();
        String username = user.get(sessionManager.UNAME);
        String id = user.get(sessionManager.UID);
        UsernameProfile.setText(username);

        //Call Display Post Method
        DisplayPost(id);

        //SwipeRefresh function
        swiperefresh = findViewById(R.id.LowerProfileConstraintLayout);
        swiperefresh.setOnRefreshListener(() -> {
            listposts.clear(); //Clear Arraylist
            DisplayPost(id);    //Re add the Data into Arraylist again
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

        });
    }

    private void DisplayPost(String id){
        String url = "http://192.168.254.100/networkingbased/DisplayUserProfile.php";

        RequestQueue q = Volley.newRequestQueue(ProfileActivity.this);

        StringRequest r = new StringRequest( //Request String type
                Request.Method.POST, //Get or Retrieve only Method of request
                url,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray userpost = oh.getJSONArray("UserPosts");

                        for (int i = 0; i < userpost.length(); i++) {
                            JSONObject al = userpost.getJSONObject(i);

                            ListPost post = new ListPost(
                                    al.optString("postUserId"),
                                    al.optString("postId"),
                                    al.optString("username"),
                                    al.optString("postDescriptions"),
                                    al.optString("postTime"),
                                    al.optString("commentsCount"),
                                    al.optString("likeCount"),
                                    al.optInt("userprofilepicture"),
                                    al.optInt("postImages")
                            );
                            listposts.add(post);
                            useradapt = new PostAdapter(ProfileActivity.this, listposts);
                            recyclerview.setAdapter(useradapt);
                            useradapt.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ProfileActivity.this, "Fetching Data From Database Failed. Please Try Again Later..", Toast.LENGTH_SHORT).show();
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
}
