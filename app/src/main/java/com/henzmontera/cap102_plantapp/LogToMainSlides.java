package com.henzmontera.cap102_plantapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogToMainSlides extends AppIntro {

    SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.createInstance(
                "Welcome User!",
                "ManGo is an agricultural mobile-based application where it helps the user to grow healthy fruit crops with the guide of the application that we will create.",
                R.drawable.logo_mangos_small_reso,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Image Classification",
                "One of the main feature of this app is that it has an image classification system where it can identify whether the captured fruit is categorized on the list (unripe, ripe, overripe, rotten).",
                R.drawable.introlog1,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Q&A Software",
                "Q&A software is provided to users and experts of the mango farming industry in order to share ideas and problems regarding in mango farming industry. The app and its users can asked questions as well as provide or receive expert answers to them. This application is particularly useful for responding to questions regarding specific industries especially in mango farming industry. Users may learn by regularly answering questions or exchanging views with other user who have expertise in mango farm industry.",
                R.drawable.introslides3,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Digital Handbook",
                "Handbook of Mango Fruit outlines the postharvest handling and packaging techniques and reviews the fruit’s processed products and byproducts that are gleaned from the processing of waste. Written for anyone involved in the production, marketing, postharvest handling, processing and by-products of mangoes, Handbook of Mango Fruit is a vital resource offering the most current information and guidelines on the burgeoning marketplace as well as the safe handling, production, and distribution of mangoes.",
                R.drawable.introslide4,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Let's Start!",
                "Thank you for the using our app ManGo. We are glad and happy! We will be always grateful for you using our app. Let's get started.",
                R.drawable.introslide5,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        // Fade Transition
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

        //Speed up or down scrolling
        setScrollDurationFactor(2);

        //Enable the color "fade" animation between two slides (make sure the slide implements SlideBackgroundColorHolder)
        setColorTransitionsEnabled(true);

        //Prevent the back button from exiting the slides
        setSystemBackButtonLocked(true);

        // set Skip Button color
        setColorSkipButton(getResources().getColor(R.color.graytext));

        // set Done Button Color
        setColorDoneText(getResources().getColor(R.color.graytext));

        //Show/hide skip button
        setSkipButtonEnabled(true);

        //Enable immersive mode (no status and nav bar)
        setImmersiveMode();

        //Enable/disable page indicators
        setIndicatorEnabled(true);

        //Show/hide ALL buttons
        setButtonsEnabled(true);

        //ColorTransition
        setColorTransitionsEnabled(true);

        // set indication color
        setIndicatorColor(getResources().getColor(R.color.white), getResources().getColor(R.color.graytext));

        // set nav bar color
        setNavBarColor(getResources().getColor(R.color.graytext));

        // set Arrow color
        setBackArrowColor(getResources().getColor(R.color.graytext));
        setNextArrowColor(getResources().getColor(R.color.graytext));

    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        myDB = openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
        ContentValues cv = new ContentValues();
        cv.put("status", "disable");
        myDB.update("logintomaincheckbox", cv, "status = 'enable'", null);
        myDB.close();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        myDB = openOrCreateDatabase("IntroSlideCheckStatus.db", 0, null);
        ContentValues cv = new ContentValues();
        cv.put("status", "disable");
        myDB.update("logintomaincheckbox", cv, "status = 'enable'", null);
        myDB.close();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        super.onDonePressed(currentFragment);
        finish();
    }

}
