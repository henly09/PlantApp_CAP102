package com.henzmontera.cap102_plantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_profile);
        //FAB Button
        BackButtonProfile = findViewById(R.id.BackButtonProfile);
        AddButtonThread = findViewById(R.id.AddThreadButton);

        emptyView = findViewById(R.id.TextviewEmpty);
        UsernameProfile = findViewById(R.id.TextviewUsernameProfile);

        UserPictureProfile = findViewById(R.id.ImageProfileThread);
        UserBackgroundProfile = findViewById(R.id.ImageProfileBackground);

        recyclerview = findViewById(R.id.recyclerView);

        //If dataset empty, Empty Textview visible, otherwise dataset visible
//        if (dataset.isEmpty()) {
//            recyclerview.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//        }
//        else {
//            recyclerview.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//        }

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible

        BackButtonProfile.setOnClickListener(view -> {
            onBackPressed();
        });

        AddButtonThread.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, AddThreadActivity.class);
            startActivity(intent);
        });



    }
}