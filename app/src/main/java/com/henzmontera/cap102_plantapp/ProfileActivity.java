package com.henzmontera.cap102_plantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class
ProfileActivity extends AppCompatActivity {

    Button EditProfile; //I doubt to use this
    TextView UsernameProfile;
    TextView emptyView;
    ImageView UserBackgroundProfile;
    ImageView UserPictureProfile;

    FloatingActionButton BackButtonProfile;
    FloatingActionButton AddButtonThread;

    RecyclerView recyclerview;

    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.activity_profile);
        //FAB Button
        BackButtonProfile = findViewById(R.id.BackButtonProfile);
        AddButtonThread = findViewById(R.id.AddAddButton);

        emptyView = findViewById(R.id.TextviewEmpty);
        UsernameProfile = findViewById(R.id.TextviewUsernameProfile);

        UserPictureProfile = findViewById(R.id.ImageProfilePost);
        UserBackgroundProfile = findViewById(R.id.ImageProfileBackground);

        //RecyclerView
        recyclerview = findViewById(R.id.recyclerVV);

        //RecyclerView Layout
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        //Call Display Post Method
        GetDisplayThread();

        //BackButton w/ Finish
        BackButtonProfile.setOnClickListener(view ->{
            onBackPressed();
            finish();
        });

        //Add Post
        AddButtonThread.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, AddPostActivity.class);
            startActivity(intent);
        });*/
    }

    public void GetDisplayThread(){
/*
        String url = "http://192.168.254.100/networkingbased/DisplayLatestPost.php";

        RequestQueue q = Volley.newRequestQueue(ProfileActivity.this);

        StringRequest r = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try{
                        JSONObject oh = new JSONObject(response);

                        JSONArray latestpost = oh.getJSONArray("LatestPost");

                        int size = latestpost.length();

                        String[] A1 = new String[size];
                        String[] A2 = new String[size];
                        String[] A3 = new String[size];
                        String[] A4 = new String[size];
                        String[] A5 = new String[size];
                        String[] A6 = new String[size];
                        String[] A7 = new String[size];
                        int[] A8 = new int[size];
                        int[] A9 = new int[size];

                        if (latestpost.length() == 0) {

                            // Live
                            recyclerview.setVisibility(View.INVISIBLE);

                            // Guest
                            emptyView.setVisibility(View.VISIBLE);
                            emptyView.requestLayout();
                        }
                        else {
                            for(int i=0; i<size; i++) {

                                JSONObject ob = latestpost.getJSONObject(i);

                                A1[i] = ob.optString("postId");
                                A2[i] = ob.optString("postUserId");
                                A3[i] = ob.optString("username");
                                A4[i] = ob.optString("postDescriptions");
                                A5[i] = ob.optString("postTime");
                                A6[i] = ob.optString("commentsCount");
                                A7[i] = ob.optString("likeCount");
                                A8[i] = ob.optInt("userprofilepicture");
                                A9[i] = ob.optInt("postImages");

                                UserPostAdapter userpostAd = new UserPostAdapter(ProfileActivity.this,A1,A2,A3,A4,A5,A6,A7,A8,A9);
                                recyclerview.setAdapter(userpostAd);
                            }

                            // LIVE
                            recyclerview.setVisibility(View.VISIBLE);
                            recyclerview.requestLayout();

                            // GUEST
                            emptyView.setVisibility(View.INVISIBLE);

                        }
                    }
                    catch(Exception e){

                    }
                }, error -> Toast.makeText(ProfileActivity.this, "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show());
        q.add(r);
*/

    }
}
