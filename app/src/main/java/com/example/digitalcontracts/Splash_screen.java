package com.example.digitalcontracts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Splash_screen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);

                    SharedPreferences sharedPreferences = getSharedPreferences("Shared", MODE_PRIVATE);

                    if (!sharedPreferences.getString("Token", "1111").matches("1111")) {
                        Intent intent = new Intent(Splash_screen.this, MainDisplay.class);
                        startActivity(intent);
                        finish();
                    } else
                    {
                        Intent intent=new Intent(Splash_screen.this,Login_screen.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
        });

        thread.start();
    }
    private boolean checkpermission() {
        return (ContextCompat.checkSelfPermission(Splash_screen.this, Manifest.permission.CAMERA)== PERMISSION_GRANTED);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestpermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }
}
