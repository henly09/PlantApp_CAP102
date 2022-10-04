package com.henzmontera.cap102_plantapp;
// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageProcCM extends AppIntro {

    SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.createInstance(
                "Guide",
                "Image classification basically includes the following three steps: Importing the image via image acquisition tools (in this case, the camera at your phone); Analysing and manipulating the image; Output in which result can be altered image or report that is based on image analysis.",
                R.drawable.cmslide,
                R.color.secondiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Capturing Image",
                "Capture the image within a focus and with high quality image in order for the app to have accurate output.",
                R.drawable.cm2slide,
                R.color.secondiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Distance",
                "In order for the size classification to work, the camera and the object must have distance between (7 inch Distance). This will identify the mango whether it is Small, Medium, or Large .",
                R.drawable.cm3slide,
                R.color.secondiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Refractometer",
                "To measure SSC (degrees Brix percentage) with a refractometer, collect the flesh from an entire mango cheek or a plug taken down to the seed and juice the entire flesh sample. Then the output result from the refractometer will be input in the brix meter converter which tells the fruit measurement of sweetness",
                R.drawable.cm4slide,
                R.color.secondiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Measure Sweetness using Brix Meter",
                "Brix is a unit of measurement and it is used to measure the amount of sugar dissolved in water. The output from the refractometer will be used and compare the the Brix table chart which tells on how the sucrose level of the mango.",
                R.drawable.cm5slide,
                R.color.secondiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Let's Start!",
                "Thank you for the using our app ManGo. We are glad and happy! We will be always grateful for you using our app. Let's get started.",
                R.drawable.cm1slide,
                R.color.secondiconcolor,
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
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

}
