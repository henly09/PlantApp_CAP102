package com.henzmontera.cap102_plantapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCommentPostActivity extends AppCompatActivity {

    private RecyclerView recyclerviewComments;
    private SessionManager sessionManager;
    private List<ListComment> listComments;
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout swipingComment;

    //THE POSTER
    private ImageView posterUserProfilePicture;
    private ImageView posterImagePostPicture;
    private TextView posterdateAndUsername;
    private TextView posterDescription;
    private TextView posterid;
    private View ViewLine;

    //THE USER
    private ImageView currentUserPicture;
    private TextView currentUserCommentTextView;
    private Button currentUserAddCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment_post);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down); // Transition during Opening this Activity
        sessionManager = new SessionManager(ViewCommentPostActivity.this);
        sessionManager.checkLogin(); //Check if Logged In
        HashMap<String, String> user = sessionManager.getUserDetail(); // Initialize User Details
        HashMap<String, String> guest = sessionManager.getGuestDetails();   // Initialize Guest Details
        listComments = new ArrayList<>();

        ViewLine = findViewById(R.id.view); //Just a view line can be seen above the poster's description

        currentUserPicture = findViewById(R.id.post_detail_currentuser_img);    // Current User's Profile Picture
        currentUserCommentTextView = findViewById(R.id.post_detail_comment);    // Current User's Comment Box
        currentUserAddCommentButton = findViewById(R.id.post_detail_add_comment_btn);   // Current User's Enter Button

        recyclerviewComments = findViewById(R.id.rv_comment);   // Recyclerview Comments

        posterdateAndUsername = findViewById(R.id.post_detail_date_name);   //Poster's Date and Username
        posterDescription = findViewById(R.id.post_detail_desc);    // Poster's Description
        posterImagePostPicture = findViewById(R.id.post_detail_img);    //Poster's POST Image
        posterUserProfilePicture = findViewById(R.id.post_detail_user_img); // Poster's User Profile Picture
        posterid = findViewById(R.id.post_posterid);    // Poster's User id

        //RecyclerView Layout
        recyclerviewComments.setHasFixedSize(true);
        recyclerviewComments.setLayoutManager(new LinearLayoutManager(ViewCommentPostActivity.this));

        String id = getIntent().getStringExtra("POSTID"); //Get Intent Value
        GetLatestComments(id);  //Get Latest Comments
        GetUserPost(id);    //Get Post
        Log.d("id", id + ""); // Result be found at "Run" Result Line

        swipingComment = findViewById(R.id.viewcomment_swiperefreshlayout);
        swipingComment.setOnRefreshListener(() -> {// Swipe Down Refresh function
            listComments.clear(); //Clears list
            GetLatestComments(id);
            swipingComment.setRefreshing(false); //False to Animation
            Toast.makeText(ViewCommentPostActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
        });

        if(guest.get(sessionManager.GNAME).equals("UserGuest000")){ // If Guest, then set default profile picture and disable some function
            currentUserPicture.setBackgroundResource(R.mipmap.ic_nature_foreground);
            currentUserCommentTextView.setFocusable(false);
            currentUserCommentTextView.setText("Comment is disable");
            currentUserCommentTextView.setTextColor( Color.parseColor("#B2BEB5"));
        } else {    // Else, User's profile picture
            String imageString = user.get(sessionManager.UIMAGE);
            currentUserPicture.setImageBitmap(StringtoImage(imageString));
        }

        currentUserAddCommentButton.setOnClickListener(view -> {
            String postid = posterid.getText().toString();
            String commenteruserid = user.get(sessionManager.UID);
            String commenttext = currentUserCommentTextView.getText().toString();
            AddComment(postid,commenteruserid,commenttext);
            currentUserCommentTextView.setText("");
        });
    }

    private void GetUserPost(String id){
        String StoreURL = "http://192.168.254.107/networkingbased/DisplayCurrentUserPost.php";
        RequestQueue q = Volley.newRequestQueue(ViewCommentPostActivity.this);
        StringRequest r = new StringRequest(
                Request.Method.POST,
                StoreURL,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray currentpost = oh.getJSONArray("CurrentPost"); //Set Array Name
                        JSONObject al = currentpost.getJSONObject(0); //Set 0 so it can only get 1 result

                        //IDs
                        posterid.setText(al.optString("postId"));
                        al.optString("postUserId");

                        //Get ImageStrings
                        String userprofileString = al.optString("userprofilepicture");
                        String postImagesString = al.optString("postImages");

                        //Set Text and Set ImageBitmap
                        posterdateAndUsername.setText(al.optString("postTime") + " | " + " by " + al.optString("username"));
                        posterDescription.setText(al.optString("postDescriptions"));
                        posterUserProfilePicture.setImageBitmap(StringtoImage(userprofileString));
                        if(postImagesString.isEmpty()){ //If Empty, set imageview to 0
                            ViewGroup.LayoutParams layoutParams = posterImagePostPicture.getLayoutParams();
                            layoutParams.width = 0;
                            layoutParams.height = 0;
                            posterImagePostPicture.setLayoutParams(layoutParams);
                        } else {
                            posterImagePostPicture.setImageBitmap(StringtoImage(postImagesString));
                        }
                    } catch (Exception e) {
                    }
                }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("postid", id);
                return param;
            }
        };
        q.add(r);
    }

    private void GetLatestComments(String postid){
        String StoreURL = "http://192.168.254.107/networkingbased/DisplayLatestComment.php";
        RequestQueue q = Volley.newRequestQueue(ViewCommentPostActivity.this);
        StringRequest r = new StringRequest(
                Request.Method.POST,
                StoreURL,
                response -> {
                    try {
                        JSONObject oh = new JSONObject(response);
                        JSONArray latestcomm = oh.getJSONArray("LatestComment"); //Set Array Name

                        for(int i = 0; i < latestcomm.length(); i++){
                            JSONObject al = latestcomm.getJSONObject(i);
                            ListComment comment = new ListComment(
                                    al.optString("commentId"),
                                    al.optString("postId"),
                                    al.optString("commenter_userid"),
                                    al.optString("username"),
                                    al.optString("userprofilepicture"),
                                    al.optString("text"),
                                    al.optString("created_at"),
                                    al.optString("edited_at")
                            );
                            listComments.add(comment);
                            commentAdapter = new CommentAdapter(ViewCommentPostActivity.this, listComments);
                            recyclerviewComments.setAdapter(commentAdapter);
                            commentAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                    }
                }, error -> {
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

    private void AddComment(String postid, String userid, String text){
        HashMap<String, String> guest = sessionManager.getGuestDetails();   // Initialize Guest Details
        if(guest.get(sessionManager.GNAME).equals("UserGuest000")){
            Toast.makeText(this, "Please login or register to comment", Toast.LENGTH_SHORT).show();
        } else {
            String StoreURL = "http://192.168.254.107/networkingbased/AddComment.php";
            RequestQueue q = Volley.newRequestQueue(ViewCommentPostActivity.this);
            StringRequest r = new StringRequest(
                    Request.Method.POST,
                    StoreURL,
                    response -> {
                        try {
                            listComments.clear();
                            GetLatestComments(postid);
                            commentAdapter.notifyDataSetChanged();
                            Toast.makeText(ViewCommentPostActivity.this, "Comment Added!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                        }
                    }, error -> {

            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> param = new HashMap<>();
                    param.put("postid", postid);
                    param.put("commenteruserid", userid);
                    param.put("text", text);
                    return param;
                }
            };
            q.add(r);
        }
    }

    //Convert from String to Bitmap Image
    private Bitmap StringtoImage(String string){
        byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}