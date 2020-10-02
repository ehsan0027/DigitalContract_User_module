package com.example.digitalcontracts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Finger_print_success extends AppCompatActivity {
Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_success);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12=getIntent();
                Intent intent1=new Intent(Finger_print_success.this,Face_detect.class);
                intent1.putExtra("id",intent12.getStringExtra("id"));
                startActivity(intent1);
            }
        });




    }
}
