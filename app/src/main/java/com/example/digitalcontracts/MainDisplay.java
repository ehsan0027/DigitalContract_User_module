package com.example.digitalcontracts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDisplay extends AppCompatActivity {
    ImageView draaawr;
    private ProgressDialog loadingBar;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_display);

        loadingBar=new ProgressDialog(this);
        if(ActivityCompat.checkSelfPermission(MainDisplay.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainDisplay.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        draaawr=findViewById(R.id.draaawr);
        draaawr.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!drawer.isDrawerOpen(Gravity.START)) drawer.openDrawer(Gravity.START);
                else drawer.closeDrawer(Gravity.END);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                navigationView.getMenu().getItem(0).setChecked(false);
                drawer.closeDrawer(GravityCompat.START);

                if (id == R.id.logout) {
                    logout();
                }

                NavigationUI.onNavDestinationSelected(item, navController);
                return true;

            }


        });
    }

    private void logout() {

        //        loadingBar.setTitle("Logging In");
        loadingBar.setMessage("Logging Out!");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();


        Intent intent1=new Intent(MainDisplay.this, Login_screen.class);
        startActivity(intent1);
        finishAffinity();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_display, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
