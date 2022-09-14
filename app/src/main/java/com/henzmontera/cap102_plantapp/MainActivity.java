package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView botnavView;
    NavController navcon;
    DrawerLayout drawer;
    /*AppBarConfiguration appbar;*/
    ActionBarDrawerToggle toggle;
    NavigationView navview;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the session manager
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin(); //Check if Logged in

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        navview = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawer,R.string.start,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Bottom Navigation setup
        botnavView = findViewById(R.id.bottomNavigationView);
        navcon = Navigation.findNavController(this,R.id.fragmentContainerView4);
        NavigationUI.setupWithNavController(botnavView,navcon);
        navview.setNavigationItemSelectedListener(this);

/*         appbar = new AppBarConfiguration.Builder(R.id.firstFragment,R.id.secondFragment,R.id.thirdFragment).build();
         NavigationUI.setupActionBarWithNavController(this, navcon, appbar);*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            switch (item.getItemId()){
                case android.R.id.home:
                    drawer.close();
                    return true;
            }
        }
        else if (!drawer.isDrawerOpen(GravityCompat.START)){
            switch (item.getItemId()){
                case android.R.id.home:
                    drawer.open();
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Login:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                drawer.closeDrawers();
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.Register:
                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                drawer.closeDrawers();
                Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.AcProfile:
                Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                drawer.closeDrawers();
                break;
            case R.id.Introduction:
                Intent intentIntro = new Intent(MainActivity.this, Guide.class);
                startActivity(intentIntro);
                drawer.closeDrawers();
                Toast.makeText(this, "Introduction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.RefractometerGuide:
                Intent intentRefractometer = new Intent(MainActivity.this, Refractometer.class);
                startActivity(intentRefractometer);
                drawer.closeDrawers();
                Toast.makeText(this, "Refractometer Guide", Toast.LENGTH_SHORT).show();
                break;
            case R.id.PenetrometerGuide:
                Intent intentPenetrometer = new Intent(MainActivity.this, Penetrometer.class);
                startActivity(intentPenetrometer);
                drawer.closeDrawers();
                Toast.makeText(this, "Refractometer Guide", Toast.LENGTH_SHORT).show();
                break;
            case R.id.AboutUs:
                Intent intentAboutUs = new Intent(MainActivity.this, AboutAuthor.class);
                startActivity(intentAboutUs);
                drawer.closeDrawers();
                Toast.makeText(this, "Authors", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Logout:
                sessionManager.logout();
                drawer.closeDrawers();
                finish();
                break;
        }
        return true;
    }
}