package com.henzmontera.cap102_plantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import kotlin.collections.ArraysKt;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botnavView;
    NavController navcon;
    AppBarConfiguration appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botnavView = findViewById(R.id.bottomNavigationView);
        navcon = Navigation.findNavController(this,R.id.fragmentContainerView4);

        appbar = new AppBarConfiguration.Builder(R.id.firstFragment,R.id.secondFragment,R.id.thirdFragment).build();

        NavigationUI.setupActionBarWithNavController(this, navcon, appbar);

        NavigationUI.setupWithNavController(botnavView,navcon);
    }
}