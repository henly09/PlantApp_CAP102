package com.henzmontera.cap102_plantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import kotlin.collections.ArraysKt;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.units.qual.A;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView botnavView;
    NavController navcon;
    DrawerLayout drawer;
    AppBarConfiguration appbar;
    ActionBarDrawerToggle toggle;
    NavigationView navview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Drawer Navigation setup
        drawer = findViewById(R.id.drawer_layout);
        navview = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawer,R.string.start,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Bottom Navigation setup
        botnavView = findViewById(R.id.bottomNavigationView);
        navcon = Navigation.findNavController(this,R.id.fragmentContainerView4);
        appbar = new AppBarConfiguration.Builder(R.id.firstFragment,R.id.secondFragment,R.id.thirdFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navcon, appbar);

        navview.setNavigationItemSelectedListener(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationUI.setupWithNavController(botnavView,navcon);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.firstFragment:
                NavController navController1 = Navigation.findNavController(this, R.id.fragmentContainerView4);
                navController1.navigateUp();
                navController1.navigate(R.id.firstFragment);
                drawer.closeDrawers();
                Toast.makeText(this, "Timeline", Toast.LENGTH_SHORT).show();
                break;
            case R.id.secondFragment:
                NavController navController2 = Navigation.findNavController(this, R.id.fragmentContainerView4);
                navController2.navigateUp();
                navController2.navigate(R.id.secondFragment);
                drawer.closeDrawers();
                Toast.makeText(this, "Handbook", Toast.LENGTH_SHORT).show();
                break;
            case R.id.thirdFragment:
                NavController navController3 = Navigation.findNavController(this, R.id.fragmentContainerView4);
                navController3.navigateUp();
                navController3.navigate(R.id.thirdFragment);
                drawer.closeDrawers();
                Toast.makeText(this, "Image Processing", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}