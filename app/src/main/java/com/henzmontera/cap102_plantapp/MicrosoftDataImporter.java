package com.henzmontera.cap102_plantapp;

import net.kibotu.timebomb.TimeBomb;

import androidx.appcompat.app.AppCompatActivity;

public class MicrosoftDataImporter extends AppCompatActivity {

    public void FirebaseTest(){

        TimeBomb.enableLogging(BuildConfig.DEBUG);
        TimeBomb.bombAfterDays(this, BuildConfig.BUILD_DATE, 0);

    }


}
