package com.henzmontera.cap102_plantapp;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Penetrometer extends AppIntro {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.createInstance(
                "How to Use a Penetrometer?",
                "(Only Available to Indian Mango Analyzer, due to un-accurate measurement of ripeness if we only use image classification) The fruit Penetrometer accurately measures fruit hardness by measuring the force required to push a plunger tip (of a certain size) into fruit and vegetables. To measure firmness of the fruit, use a Penetrometer. Use a 8mm tip on a GY-3 Penetrometer to test the mango flesh with the skin removed.if the 8 mm stamp is used, read on the inner scale.",
                R.drawable.pene,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "First Step:",
                "Hold the fruit with one hand on a solid surface.",
                R.drawable.pene1,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Second Step:",
                "Then push the stamp of the penetrometer with uniform pressure into the skinless flesh. ",
                R.drawable.pene2,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Third Step:",
                "On the stamp, the permissible depth of penetration is determined by a milled marking ring. ",
                R.drawable.pene3,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Fourth Step:",
                "Avoid jerky or lateral movements during the penetration process. ",
                R.drawable.pene4,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Final Step",
                "The measured value is read and recorded. It will be ready to be converted in the firm converter in this app.",
                R.drawable.pene5,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Let's Start!",
                "Thank you for the using our app ManGo. We are glad and happy! We will be always grateful for you using our app. Let's get started.",
                R.drawable.refractometerslide9,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
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
        setColorSkipButton(getResources().getColor(R.color.darkgray));

        // set Done Button Color
        setColorDoneText(getResources().getColor(R.color.darkgray));

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
        setIndicatorColor(getResources().getColor(R.color.darkgray),getResources().getColor(R.color.white));

        // set nav bar color
        setNavBarColor(getResources().getColor(R.color.darkgray));

        // set Arrow color
        setBackArrowColor(getResources().getColor(R.color.darkgray));
        setNextArrowColor(getResources().getColor(R.color.darkgray));

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
