package com.example.digitalcontracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.digitalcontracts.Models.LOGINOTP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_verification extends AppCompatActivity {
    PinView firstPinView,firstPinView2;
    Button verify;
    private ProgressDialog loadingBar;
    String FCM="no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        firstPinView=findViewById(R.id.firstPinView);
        verify=findViewById(R.id.verify);
        loadingBar=new ProgressDialog(this);
        firstPinView2=findViewById(R.id.firstPinView2);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstPinView.getText().toString().length() < 4)
                {
                    Toast.makeText(OTP_verification.this, "Enter Mobile OTP", Toast.LENGTH_SHORT).show();
                }
                else if(firstPinView2.getText().toString().length() < 4)
                {
                    Toast.makeText(OTP_verification.this, "Enter Email OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    hitapi();
                }
            }
        });
    }

    private void hitapi() {

        FirebaseApp.initializeApp(OTP_verification.this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful())
                {
                    FCM=task.getResult().getToken().toString();

                    loadingBar.setMessage("verifying");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(false);

                    Intent intent=getIntent();

//        Toast.makeText(this, ""+intent.getStringExtra("cnic"), Toast.LENGTH_SHORT).show();

                    Call<LOGINOTP> call = Retrofit_Client_URL.getInstance().getapi().otp_verify(intent.getStringExtra("cnic"),firstPinView.getText().toString(),firstPinView2.getText().toString(),FCM);

                    call.enqueue(new Callback<LOGINOTP>() {
                        @Override
                        public void onResponse(@NonNull Call<LOGINOTP> call, @NonNull Response<LOGINOTP> response) {

                            try {
                                if(response.body().getMessage().matches("verified"))
                                {

                                    SharedPreferences.Editor editor = getSharedPreferences("Shared", MODE_PRIVATE).edit();
                                    //  Toast.makeText(Login_Screen.this, ""+success.getSuccess().getToken(), Toast.LENGTH_SHORT).show();
                                    editor.putString("Token", "Bearer " + response.body().getToken().toString());
                                    editor.putString("id", response.body().getId().toString());
                                    editor.apply();
                                    editor.commit();

                                    Intent intent1=new Intent(OTP_verification.this, MainDisplay.class);
                                    startActivity(intent1);
                                }
                                else
                                {
                                    Toast.makeText(OTP_verification.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(OTP_verification.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.cancel();
                        }

                        @Override
                        public void onFailure(Call<LOGINOTP> call, Throwable t) {
                            loadingBar.cancel();
//                Toast.makeText(OTP_verification.this, "Failed to hit API "+t, Toast.LENGTH_SHORT).show();
                        }


                    });


                }
            }
        });



    }
}
