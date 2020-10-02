package com.example.digitalcontracts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalcontracts.Models.Login_api;
import com.google.firebase.FirebaseApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Login_screen extends AppCompatActivity {
    Button login_button;
EditText cnic;
    private ProgressDialog loadingBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        login_button=findViewById(R.id.login_button);
        cnic=findViewById(R.id.cnic);
        loadingBar=new ProgressDialog(this);
        if(checkpermission())
        {

            // Toast.makeText(MainActivity.this, "permission", Toast.LENGTH_SHORT).show();
        }else if(!checkpermission())
        {
            requestpermission();
        }
        checkpermissions();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnic.getText().toString().isEmpty())
                {
                    cnic.setError("Enter CNIC");
                }
//              else  if(cnic.getText().toString().length() > 15)
//                {
//                    cnic.setError("CNIC is not valid");
//                }
                else
                {
                   hitapi();
                }
            }
        });


//        cnic.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DEL) {
//
//                }
//                else {
//                    if (cnic.getText().length() == 5 || cnic.getText().length() == 13) {
//                        cnic.append("-");
//                    }
//                }
//                return false;
//            }
//        });
//        if(checkpermission())
//        {
//
//            // Toast.makeText(MainActivity.this, "permission", Toast.LENGTH_SHORT).show();
//        }else if(!checkpermission())
//        {
//            requestpermission();
//        }
//        if(ActivityCompat.checkSelfPermission(Login_screen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(Login_screen.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
//            return;
//        }
    }

    private void checkpermissions() {
        if(ActivityCompat.checkSelfPermission(Login_screen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Login_screen.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
    }

    private void hitapi() {






//        loadingBar.setTitle("Logging In");
        loadingBar.setMessage("Authenticating CNIC");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        Call<Login_api> call = Retrofit_Client_URL.getInstance().getapi().login(cnic.getText().toString());

        call.enqueue(new Callback<Login_api>() {
            @Override
            public void onResponse(@NonNull Call<Login_api> call, @NonNull Response<Login_api> response) {

              if(response.body().getMessage().matches("OTP sent"))
              {
                  Intent intent=new Intent(Login_screen.this,OTP_verification.class);
                  intent.putExtra("cnic",cnic.getText().toString());
                  startActivity(intent);
              }
              else
              {
                  Toast.makeText(Login_screen.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
              }

loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<Login_api> call, Throwable t) {
                loadingBar.cancel();
//                Toast.makeText(Login_screen.this, "Failed to hit API "+t, Toast.LENGTH_SHORT).show();
            }


        });



    }
    private boolean checkpermission() {

        return (ContextCompat.checkSelfPermission(Login_screen.this, Manifest.permission.CAMERA)== PERMISSION_GRANTED);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestpermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }
}
