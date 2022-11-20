package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView botnavView;
    private NavController navcon;
    private DrawerLayout drawer;
    /*AppBarConfiguration appbar;*/
    private ActionBarDrawerToggle toggle;
    private NavigationView navview;
    private SessionManager sessionManager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the session manager
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin(); //Check if Logged In

        //If Not Logged In Guest Mode Initiate
        if(!sessionManager.isLoggin()){
            String Gname = "UserGuest000";
            String Gid = "69142";
            sessionManager.createGuestSession(Gname,Gid);
        }

        //Menu Items Visible(Guest/User)
        HashMap<String, String> guest = sessionManager.getGuestDetails();
        if(guest.get(sessionManager.GNAME).equals("UserGuest000")){
            GuestItem(); //Only Guest Can Access
        } else {
            UserItem(); //Only User Access
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //FullScreen

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Navigation Drawer Setup
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

    private void GuestItem(){
        navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.Register).setVisible(true);
        menu.findItem(R.id.Login).setVisible(true);

        menu.findItem(R.id.Logout).setVisible(false);
        menu.findItem(R.id.AcProfile).setVisible(false);
    }

    private void UserItem(){
        navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.Register).setVisible(false);
        menu.findItem(R.id.Login).setVisible(false);

        menu.findItem(R.id.Logout).setVisible(true);
        menu.findItem(R.id.AcProfile).setVisible(true);
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
                finish();
                break;
            case R.id.Register:
                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                drawer.closeDrawers();
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
                break;
            case R.id.RefractometerGuide:
                Intent intentRefractometer = new Intent(MainActivity.this, Refractometer.class);
                startActivity(intentRefractometer);
                drawer.closeDrawers();
                break;
            case R.id.PenetrometerGuide:
                Intent intentPenetrometer = new Intent(MainActivity.this, Penetrometer.class);
                startActivity(intentPenetrometer);
                drawer.closeDrawers();
                break;
            case R.id.AboutUs:
                Intent intentAboutUs = new Intent(MainActivity.this, AboutAuthor.class);
                startActivity(intentAboutUs);
                drawer.closeDrawers();
                break;
            case R.id.Logout:
                sessionManager.logout();
                Toast.makeText(this, "Logout..", Toast.LENGTH_SHORT).show();
                drawer.closeDrawers();
                finish();
                break;
        }
        return true;
    }
}