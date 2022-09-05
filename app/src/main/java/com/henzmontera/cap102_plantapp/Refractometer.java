package com.henzmontera.cap102_plantapp;

import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Refractometer extends AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.createInstance(
                "How to Use a Refractometer?",
                "A Refractometer is a tool that can determine the concentration of a particular substance in a liquid solution. It uses the principle of refraction, which describes how light bends as it crosses the boundary between one medium and another.",
                R.drawable.logo_mangos_small_reso,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Brix Refractometer",
                "Brix Refractometer have a readout that gives the percentage of sucrose, and are used in the food and beverage industry for quality control.",
                R.drawable.introlog1,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Using a Handheld Brix Refractometer",
                "To take a measurement with a handheld analog refractometer, follow these steps and read the manufacturer's instructions:",
                R.drawable.introslides3,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "First Step:",
                "Calibrate the Refractometer with a standard solution before use. Since the reading will be affected by temperature changes, it's best to calibrate at the temperature of the test environment. If this is not possible, correction charts may be used. Some refractometers have automatic temperature correction (ATC), a feature that allows the instrument to automatically correct for temperature differences.",
                R.drawable.introslide4,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Second Step:",
                "Place a small amount of liquid (usually 2–5 drops) on the prism, and secure the cover plate. This will evenly distribute the liquid on the prism.",
                R.drawable.imslide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Third Step:",
                "Point the prism end of the refractometer toward a light source and focus the eyepiece until the scale is clearly visible.",
                R.drawable.im1slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Final Step:",
                "Read the scale value at the point where the dark and light portions meet.",
                R.drawable.im2slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Measurement of Sweetness using Brix Meter",
                "To measure SSC (degrees Brix percentage) with a Brix Refractometer, collect the flesh from an entire mango cheek or a plug taken down to the seed and juice the entire flesh sample. Then the output result from the refractometer will be input in the Brix Meter Converter which tells the fruit measurement of sweetness",
                R.drawable.im3slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Let's Start!",
                "Thank you for the using our app ManGo. We are glad and happy! We will be always grateful for you using our app. Let's get started.",
                R.drawable.im5slide,
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
