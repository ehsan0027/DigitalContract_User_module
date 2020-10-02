package com.example.digitalcontracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.digitalcontracts.Adapters.PDF_ADAPTER;
import com.example.digitalcontracts.Models.Login_api;
import com.example.digitalcontracts.Models.PDF;
import com.example.digitalcontracts.Models.PDF_API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog loadingBar;
ImageView toolbar;
    RecyclerView applications;
LinearLayout buttons;
    Button agree,disagree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingBar=new ProgressDialog(this);
        toolbar=findViewById(R.id.toolbar);
        agree=findViewById(R.id.agree);
        buttons=findViewById(R.id.buttons);
        applications=findViewById(R.id.applications);
        disagree=findViewById(R.id.disagree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accept_record();
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
reject_record();
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        applications.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        setthevalues();



        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            return;
        }


        Intent intent13=getIntent();
//        Toast.makeText(this, ""+intent13.getStringExtra("status"), Toast.LENGTH_SHORT).show();
        if(intent13.getStringExtra("status").matches("1")) //accepted
        {
            buttons.setVisibility(View.GONE);
        }else if(intent13.getStringExtra("status").matches("0")) //rejected
        {
            buttons.setVisibility(View.GONE);
        }
        else
        {
            buttons.setVisibility(View.VISIBLE);
        }


    }

    private void setthevalues() {

//        loadingBar.setTitle("Logging In");
        loadingBar.setMessage("please wait");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        final Intent intent=getIntent();

        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        Call<PDF_API> call = Retrofit_Client_URL.getInstance().getapi().pdfs(sharedPreferences.getString("Token","1111"),intent.getStringExtra("id"));

        call.enqueue(new Callback<PDF_API>() {
            @Override
            public void onResponse(@NonNull Call<PDF_API> call, @NonNull Response<PDF_API> response) {

                if(response.code()==401)
                {
                    loadingBar.cancel();

                    Intent intent1=new Intent(MainActivity.this,Login_screen.class);
                    startActivity(intent1);
                    finishAffinity();
                }
                else if(response.body().getMessage().matches("result found"))
                {

                    ArrayList<com.example.digitalcontracts.Models.PDF> arrayList=new ArrayList<>();
                    arrayList= (ArrayList<PDF>) response.body().getResult();

//                    Toast.makeText(MainActivity.this, ""+arrayList.size(), Toast.LENGTH_SHORT).show();
                    applications.setAdapter(new PDF_ADAPTER(arrayList,MainActivity.this));


                }

                loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<PDF_API> call, Throwable t) {
                loadingBar.cancel();
//                Toast.makeText(MainActivity.this, "Failed to hit API "+t, Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void reject_record() {

//        loadingBar.setTitle("Logging In");
        loadingBar.setMessage("please wait");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        final Intent intent=getIntent();

        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        Call<Login_api> call = Retrofit_Client_URL.getInstance().getapi().accept_record(sharedPreferences.getString("Token","1111"),intent.getStringExtra("id"),"0");

        call.enqueue(new Callback<Login_api>() {
            @Override
            public void onResponse(@NonNull Call<Login_api> call, @NonNull Response<Login_api> response) {

                if(response.body().getMessage().matches("status updated"))
                {
                finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<Login_api> call, Throwable t) {
                loadingBar.cancel();
                Toast.makeText(MainActivity.this, "Failed to hit API "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Accept_record() {

//        loadingBar.setTitle("Logging In");
        loadingBar.setMessage("please wait");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        final Intent intent=getIntent();

        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        Call<Login_api> call = Retrofit_Client_URL.getInstance().getapi().accept_record(sharedPreferences.getString("Token","1111"),intent.getStringExtra("id"),"1");

        call.enqueue(new Callback<Login_api>() {
            @Override
            public void onResponse(@NonNull Call<Login_api> call, @NonNull Response<Login_api> response) {

                if(response.body().getMessage().matches("status updated"))
                {
Intent intent1=new Intent(MainActivity.this,CNIC.class);
intent1.putExtra("id",intent.getStringExtra("id"));
startActivity(intent1);
//                finish();

                }
                else
                {
                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<Login_api> call, Throwable t) {
                loadingBar.cancel();
                Toast.makeText(MainActivity.this, "Failed to hit API "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
