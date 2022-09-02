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
                "This is a ManGo in java of AppIntro library, with a custom background on each slide!",
                R.drawable.firstslide,
                R.color.firsticoncolor,
                R.color.graytext,
                R.color.graytext
        ));

        addSlide(AppIntroFragment.createInstance(
                "Distance",
                "This library offers developers the ability to add clean app intros at the start of their apps.",
                R.drawable.secondslide,
                R.color.secondiconcolor,
                R.color.graytext,
                R.color.graytext
        ));

        addSlide(AppIntroFragment.createInstance(
                "Using Refractometer",
                "The library offers a lot of customization, while keeping it simple for those that like simple.",
                R.drawable.thirdslide,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext
        ));

        addSlide(AppIntroFragment.createInstance(
                "Measure Sweetness using Brix Meter",
                "Feel free to explore the rest of the library demo!",
                R.drawable.fourthslide,
                R.color.fourthiconcolor,
                R.color.graytext,
                R.color.graytext
        ));

        addSlide(AppIntroFragment.createInstance(
                "Let's Start!",
                "Feel free to explore the rest of the library demo!",
                R.drawable.fifthslide,
                R.color.fifthiconcolor,
                R.color.graytext,
                R.color.graytext
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
        setIndicatorColor(getResources().getColor(R.color.fifthiconcolor), getResources().getColor(R.color.graytext));

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
