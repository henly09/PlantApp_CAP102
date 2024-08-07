package com.henzmontera.cap102_plantapp;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class CarInfo_sec extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info_sec);

        ImageSlider imageSlider = findViewById(R.id.imgslider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.carabaomango1));
        slideModels.add(new SlideModel(R.drawable.carabaomango2));
        slideModels.add(new SlideModel(R.drawable.carabaomango3));
        imageSlider.setImageList(slideModels, true);


    }

}