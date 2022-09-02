package com.henzmontera.cap102_plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
            case R.id.Login:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                drawer.closeDrawers();
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.Register:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                drawer.closeDrawers();
                Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.Introduction:
                Intent intentIntro = new Intent(this, Guide.class);
                startActivity(intentIntro);
                drawer.closeDrawers();
                Toast.makeText(this, "Introduction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.AboutUs:
                Intent intentAboutUs = new Intent(this, AboutAuthor.class);
                startActivity(intentAboutUs);
                drawer.closeDrawers();
                Toast.makeText(this, "Authors", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}