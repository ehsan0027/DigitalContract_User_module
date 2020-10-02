package com.example.digitalcontracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.digitalcontracts.Models.Status_percent;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Digital_signatures extends AppCompatActivity {
    com.github.gcacace.signaturepad.views.SignaturePad mSignaturePad;
    ImageView toolbar;
    Button next;
    private ProgressDialog loadingBar;

    public static File Signature_file;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_signatures);
        toolbar=findViewById(R.id.toolbar);
        next=findViewById(R.id.next);
        loadingBar=new ProgressDialog(this);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
//                Intent intent=new Intent(Digital_signatures.this,Finger_print_success.class);
//                startActivity(intent);
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bitmap=  mSignaturePad.getSignatureBitmap();
//                    bitmap= getResizedBitmap(bitmap,900);
                    Signature_file=new File(String.valueOf(BitmapToFile(Digital_signatures.this,bitmap,"signature.jpg")));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(Signature_file==null)
                {
                    Toast.makeText(Digital_signatures.this, "Sign the document", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setMessage("Matching Signatures");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(false);
                    uploadsignatures();

                }

            }
        });
    }

    private void uploadsignatures() {

        MultipartBody.Part Signature_file_part;
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("image/*"), Signature_file);
        Signature_file_part = MultipartBody.Part.createFormData("verify_image",   Signature_file.getName(), descriptionPart);

        Intent intent=getIntent();
        RequestBody id = RequestBody.create(MediaType.parse("application_id"),intent.getStringExtra("id"));


        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        Call<RequestBody> call = Retrofit_Client_URL.getInstance().getapi().sigantures(sharedPreferences.getString("Token","1111"),id,Signature_file_part);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(@NonNull Call<RequestBody> call, @NonNull Response<RequestBody> response) {

//                Toast.makeText(Digital_signatures.this, "Failed to hit API ", Toast.LENGTH_SHORT).show();

                loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
//                Toast.makeText(Digital_signatures.this, "Application has been submitted", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Digital_signatures.this, Finger_print_success.class);
//                Intent intent1=getIntent();
//                intent.putExtra("id",intent1.getStringExtra("id"));
//                startActivity(intent);
//                finishAffinity();
checkkro();
//mSignaturePad.clear();
//mSignaturePad.clearView();

//                loadingBar.cancel();
            }


        });


    }

    private void checkkro() {
        Intent intent=getIntent();

        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        Call<Status_percent> call1 = Retrofit_Client_URL.getInstance().getapi().sig_percent(sharedPreferences.getString("Token","1111"),intent.getStringExtra("id"));

        call1.enqueue(new Callback<Status_percent>() {
            @Override
            public void onResponse(@NonNull Call<Status_percent> call, @NonNull Response<Status_percent> response) {

                String vlue=response.body().getResult().getStatus().toString();
                vlue= vlue.replace("\r\n","");

                if(Integer.valueOf(vlue) < 100)
                {
                    show_dialogue();
                    mSignaturePad.clear();
//                    Toast toast = Toast.makeText(Digital_signatures.this, "Did not match", Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                }
                else
                {
                    Intent intent12=getIntent();
                    Intent intent1=new Intent(Digital_signatures.this,Finger_print_success.class);
                    intent1.putExtra("id",intent12.getStringExtra("id"));
                    startActivity(intent1);
                }
loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<Status_percent> call, Throwable t) {
loadingBar.cancel();
            }


        });


    }

    private File BitmapToFile(Activity mAct, Bitmap bitmap, String filename) {
        File f = new File(mAct.getCacheDir(), filename);
        try {
            f.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private void show_dialogue() {
        final AlertDialog.Builder alertadd = new AlertDialog.Builder(Digital_signatures.this);
        LayoutInflater factory = LayoutInflater.from(Digital_signatures.this);
        final View view = factory.inflate(R.layout.otp_hogya_verified, null);
        alertadd.setView(view);
        alertadd.setNeutralButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {


            }
        });

        alertadd.show();

    }
}
