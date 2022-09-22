package com.henzmontera.cap102_plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class AppleInfo_sec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_info_sec);


        ImageSlider imageSlider = findViewById(R.id.imgslider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.applemango1));
        slideModels.add(new SlideModel(R.drawable.applemango2));
        slideModels.add(new SlideModel(R.drawable.applemango3));
        imageSlider.setImageList(slideModels, true);
    }
}