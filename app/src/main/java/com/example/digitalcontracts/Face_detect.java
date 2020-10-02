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
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.camerakit.CameraKitView;
import com.example.digitalcontracts.Models.Status_percent;
import com.tzutalin.dlib.Constants;
import com.tzutalin.dlib.FaceDet;
import com.tzutalin.dlib.VisionDetRet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Face_detect extends AppCompatActivity {
    File cascFile;
    TextureView texture;
    ImageView toolbar;
    TextView text;
    public static File FACE=null;
    private ProgressDialog loadingBar;

    private CameraKitView cameraKitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadingBar=new ProgressDialog(this);


        cameraKitView = findViewById(R.id.camera);
        cameraKitView.onStart();
        final Button image_cap=findViewById(R.id.image_cap);




        image_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setMessage("Processing Image");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);


                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, byte[] bytes) {


//                        Toast.makeText(Face_detect.this, "clicked"+bytes.length, Toast.LENGTH_SHORT).show();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inMutable = true;
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                        ArrayList<Point> landmarks = null;
//                        Toast.makeText(Face_detect.this, ""+bmp, Toast.LENGTH_SHORT).show();
                        int rectBottom = 0;
                        String label="kj";
                        FaceDet faceDet = new FaceDet(Constants.getFaceShapeModelPath());
//                Bitmap bitmap = BitmapFactory.decodeFile("Image Path");
                        List<VisionDetRet> results = faceDet.detect(bmp);
                        for (final VisionDetRet ret : results) {
                            label = ret.getLabel();
                            int rectLeft = ret.getLeft();
                            int rectTop = ret.getTop();
                            int rectRight = ret.getRight();
                            rectBottom = ret.getBottom();
                            // Get 68 landmark points
                            landmarks = ret.getFaceLandmarks();
                            for (Point point : landmarks) {
                                int pointX = point.x;
                                int pointY = point.y;
                            }

                        }

                        if(rectBottom==0)
                        {
                            Toast.makeText(Face_detect.this, "No Face detected", Toast.LENGTH_SHORT).show();
                            image_cap.setText("Re-scan FACE");
                            loadingBar.cancel();
                        }
                        else
                        {
                            FACE=new File(String.valueOf(BitmapToFile(Face_detect.this,bmp,"Face.jpg")));
                            gotonextscreen();
//                            Toast.makeText(Face_detect.this, "Mill gya", Toast.LENGTH_SHORT).show();
                        }
//                        loadingBar.cancel();

                    }


                });
            }
        });
    }

    private void gotonextscreen() {



        MultipartBody.Part Signature_file_part;
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("image/*"), FACE);
        Signature_file_part = MultipartBody.Part.createFormData("profile_image",   FACE.getName(), descriptionPart);

        Intent intent=getIntent();
        RequestBody id = RequestBody.create(MediaType.parse("application_id"),intent.getStringExtra("id"));



        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);



        Call<RequestBody> call = Retrofit_Client_URL.getInstance().getapi().face(sharedPreferences.getString("Token","1111"),id,Signature_file_part);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(@NonNull Call<RequestBody> call, @NonNull Response<RequestBody> response) {

//                Toast.makeText(Face_detect.this, "Failed to hit API ", Toast.LENGTH_SHORT).show();

                loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {

////                Toast.makeText(Face_detect.this, "Application has been submitted", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Face_detect.this, Finger_print_success.class);
//                startActivity(intent);
//                finishAffinity();

                checkkro();
//                loadingBar.cancel();
            }


        });





    }

    private void checkkro() {

        final Intent intent=getIntent();

        final SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        Call<Status_percent> call = Retrofit_Client_URL.getInstance().getapi().face_percent(sharedPreferences.getString("Token","1111"),intent.getStringExtra("id"));

        call.enqueue(new Callback<Status_percent>() {
            @Override
            public void onResponse(@NonNull Call<Status_percent> call, @NonNull Response<Status_percent> response) {

                loadingBar.cancel();

                if(Integer.valueOf(response.body().getResult().getStatus().toString()) < 50)
                {
                    show_dialogue();
//                    Toast toast = Toast.makeText(Face_detect.this, "Did not match", Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                }
                else
                {
                    Intent intent1=new Intent(Face_detect.this,Application_submitted.class);
                    intent1.putExtra("id",intent.getStringExtra("id"));
                    intent1.putExtra("percent",response.body().getResult().getStatus().toString());
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);

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

    private void show_dialogue() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(Face_detect.this);
        LayoutInflater factory = LayoutInflater.from(Face_detect.this);
        final View view = factory.inflate(R.layout.face_verifiedddd, null);
        alertadd.setView(view);
        alertadd.setNeutralButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });

        alertadd.show();

    }
}
