package com.henzmontera.cap102_plantapp;

import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Guide extends AppIntro {

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
                "Mango Analyzer",
                "One of the main feature of this app is that it has an image classification system where it can identify whether the harvested fruit is categorized on the list (unripe, ripe, overripe, rotten).",
                R.drawable.introlog1,
                R.color.thirdiconcolor,
                R.color.graytext,
                R.color.graytext,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Q&A System",
                "Q&A System is provided to users and experts of the mango farming industry in order to share ideas and problems regarding in mango farming industry. The app and its users can asked questions as well as provide or receive expert answers to them. This application is particularly useful for responding to questions regarding specific industries especially in mango farming industry. Users may learn by regularly answering questions or exchanging views with other user who have expertise in mango farm industry.",
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
                "Guide For Image Classification",
                "Image classification basically includes the following three steps: Importing the image via image acquisition tools (in this case, the camera at your phone); Analysing and manipulating the image; Output in which result can be altered image or report that is based on image analysis.",
                R.drawable.imslide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Capturing Image",
                "Capture the image within a focus and with high quality image in order for the app to have accurate output.",
                R.drawable.im1slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Distance",
                "In order for the size classification to work, the camera and the object must have distance between (7 inch Distance). This will identify the mango whether it is Small, Medium, or Large .",
                R.drawable.im2slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Refractometer",
                "To measure SSC (degrees Brix percentage) with a refractometer, collect the flesh from an entire mango cheek or a plug taken down to the seed and juice the entire flesh sample. Then the output result from the refractometer will be input in the brix meter converter which tells the fruit measurement of sweetness",
                R.drawable.im3slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Penetrometer",
                "(Only Available to Indian Mango Analyzer, due to un-accurate measurement of ripeness if we only use image classification) To measure firmness of the fruit, Use Penetrometer. Use a 8mm tip on a GY-3 Penetrometer to test the mango flesh with the skin removed.if the 8 mm stamp is used, read on the inner scale. As the firmness of the fruits flesh is to be measured, the testing area has to be free from skin.",
                R.drawable.im6slide,
                R.color.thirdiconcolor,
                R.color.darkgray,
                R.color.darkgray,
                R.font.ubuntu_m,
                R.font.ubuntu_mi
        ));

        addSlide(AppIntroFragment.createInstance(
                "Measure Sweetness using Brix Meter",
                "Brix is a unit of measurement and it is used to measure the amount of sugar dissolved in water. The output from the refractometer will be used and compare the the Brix table chart which tells on how the sucrose level of the mango.",
                R.drawable.im4slide,
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
