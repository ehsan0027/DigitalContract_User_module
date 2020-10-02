package com.example.digitalcontracts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Application_submitted extends AppCompatActivity {
Button dashboard;
TextView percent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_submitted);
        dashboard=findViewById(R.id.dashboard);
        percent=findViewById(R.id.percent);



        Intent intent1=getIntent();
        percent.setText("Your face has been matched "+  intent1.getStringExtra("percent")+"%");


        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Application_submitted.this,MainDisplay.class);
                startActivity(intent1);
                finishAffinity();
            }
        });




    }
}
