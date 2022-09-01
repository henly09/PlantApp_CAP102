package com.henzmontera.cap102_plantapp;

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

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo_mangos_small_reso)
                .setDescription("ManGo is an agricultural mobile-based application where it helps the user to grow healthy" +
                        "fruit crops with the guide of the application that we will create.")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Author's Facebook")
                .addFacebook("mhax.ter","John Henly Montera")
                .addFacebook("cycyulike","Cyrex Joshua Cuizon")
                .addFacebook("alvinbula","John Alvin Bula")
                .addGroup("Author's Email")
                .addEmail("johnhenly.montera@hcdc.edu.ph","johnhenly.montera@hcdc.edu.ph")
                .addEmail("johnalvin.bula@hcdc.edu.ph","johnalvin.bula@hcdc.edu.ph")
                .addEmail("cyrexjoshua.cuizon@hcdc.edu.ph","cyrexjoshua.cuizon@hcdc.edu.ph")
                .addGroup("Author's Github Account")
                .addWebsite("https://github.com/BnkuBnku", "Cyrex Joshua Cuizon")
                .addWebsite("https://github.com/alvinbula24", "John Alvin Bula")
                .addWebsite("https://github.com/henly09", "John Henly A. Montera")
                .addGroup("App's Source Code")
                .addGitHub("https://github.com/henly09/PlantApp_CAP102", "Source Code")
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