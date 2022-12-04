package com.henzmontera.cap102_plantapp;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutAuthor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbartheme));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.intrologo2_lowres)
                .setDescription("ManGo is an agricultural mobile-based application where it helps the user to grow healthy" +
                        "fruit crops with the guide of the application that we've create.")
                .addItem(new Element().setTitle("Version 0.1"))
                .addGroup("Author's Facebook")
                .addFacebook("mhax.ter","John Henly Montera")
                .addFacebook("cycyulike","Cyrex Joshua Cuizon")
                .addFacebook("alvinbula","John Alvin Bula")
                .addGroup("Author's Email")
                .addEmail("johnhenly.montera@hcdc.edu.ph","johnhenly.montera@hcdc.edu.ph")
                .addEmail("johnalvin.bula@hcdc.edu.ph","johnalvin.bula@hcdc.edu.ph")
                .addEmail("cyrexjoshua.cuizon@hcdc.edu.ph","cyrexjoshua.cuizon@hcdc.edu.ph")
                .addGroup("Author's Github Account")
                .addGitHub("BnkuBnku", "Cyrex Joshua Cuizon")
                .addGitHub("alvinbula24", "John Alvin Bula")
                .addGitHub("henly09", "John Henly A. Montera")
                .addGroup("Application's Source Code")
                .addGitHub("henly09/PlantApp_CAP102", "Source Code")
                .addItem(createCopyright())
                .create();


        setContentView(aboutPage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private Element createCopyright()
    {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by ManGo©", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIconDrawable(R.drawable.ic_nature);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(v -> Toast.makeText(AboutAuthor.this,copyrightString,Toast.LENGTH_SHORT).show());
        return copyright;
    }
}