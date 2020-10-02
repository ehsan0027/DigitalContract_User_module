package com.example.digitalcontracts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CNIC extends AppCompatActivity {
Button next;
    public int seelected=0;
    private ProgressDialog loadingBar;

    public static File cnic_front_file=null,cnic_back_file=null;
ImageView cnicBack,cnicFront;
    private static  final  int REQUEST_CAMERA=1;
    private static  final  int SELECT_FILE=2;
    private static  final  int SELECT_PDF=3;
    ImageView toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_n_i_c);
        next=findViewById(R.id.next);
        cnicBack=findViewById(R.id.cnic_back);
        loadingBar=new ProgressDialog(this);
        cnicFront=findViewById(R.id.cnic_front);
        toolbar=findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnic_front_file==null)
                {
                    Toast.makeText(CNIC.this, "Select CNIC Front", Toast.LENGTH_SHORT).show();
                }else if(cnic_back_file==null)
                {
                    Toast.makeText(CNIC.this, "Select CNIC back", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    send_images();
                }


            }
        });

        cnicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkpermission())
                    {
                        seelected=1;
                        Addimages(1);

                        // Toast.makeText(MainActivity.this, "permission", Toast.LENGTH_SHORT).show();
                    }else if(!checkpermission())
                    {

                        requestpermission();
                    }

                }
                else{
                    seelected=1;
                    Addimages(1);
                }
            }
        });

        cnicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkpermission())
                    {
                        seelected=2;
                        Addimages(2);

                        // Toast.makeText(MainActivity.this, "permission", Toast.LENGTH_SHORT).show();
                    }else if(!checkpermission())
                    {

                        requestpermission();
                    }

                }
                else{
                    seelected=2;
                    Addimages(2);
                }
            }
        });



    }

    private void send_images() {


        loadingBar.setMessage("Authenticating CNIC");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        MultipartBody.Part CNIC_BACK_PART;
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("image/*"), cnic_back_file);
        CNIC_BACK_PART = MultipartBody.Part.createFormData("cnic_front_verify",   cnic_back_file.getName(), descriptionPart);

        MultipartBody.Part CNIC_FRONT_PART;
        descriptionPart = RequestBody.create(MediaType.parse("image/*"), cnic_front_file);
        CNIC_FRONT_PART = MultipartBody.Part.createFormData("cnic_back_verify",   cnic_front_file.getName(), descriptionPart);



        SharedPreferences sharedPreferences=getSharedPreferences("Shared",MODE_PRIVATE);

        final Intent intent=getIntent();
String id=intent.getStringExtra("id");
        RequestBody id1 = RequestBody.create(MediaType.parse("application_id"),id);

        Call<RequestBody> call = Retrofit_Client_URL.getInstance().getapi().cnic_pic(sharedPreferences.getString("Token","1111"),id1,CNIC_FRONT_PART,CNIC_BACK_PART);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(@NonNull Call<RequestBody> call, @NonNull Response<RequestBody> response) {

//                Toast.makeText(CNIC.this, "Failed to hit API", Toast.LENGTH_SHORT).show();

                loadingBar.cancel();
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                loadingBar.cancel();
                Intent intent1=new Intent(CNIC.this,Digital_signatures.class);
                intent1.putExtra("id",intent.getStringExtra("id"));
                startActivity(intent1);
            }


        });




    }




    private void Addimages(final int a) {

        final  CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(CNIC.this);
        builder.setTitle("Add image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera"))
                {

                    if(a==1)
                    {
                        OpenCamera(1);
                    }
                    else {
                        OpenCamera(2);
                    }
                }
                else if (items[which].equals("Gallery"))
                {
                    if(ActivityCompat.checkSelfPermission(CNIC.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(CNIC.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                        return;
                    }
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                    intent.setType("image/*");

                    startActivityForResult(intent, SELECT_FILE);

                }
//                else if (items[which].equals("Document"))
//                {
//                    if(ActivityCompat.checkSelfPermission(Scan_cnic_form_9.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
//                    {
//                        ActivityCompat.requestPermissions(Scan_cnic_form_9.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
//                        return;
//                    }
//                    Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    //  intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//                    intent.setType("application/pdf");
//
//                    startActivityForResult(intent, SELECT_FILE);
//
//
//
//                }

            }
        });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void OpenCamera(int a) {
        Intent galleryIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(galleryIntent, REQUEST_CAMERA);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestpermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }

    private boolean checkpermission() {
        return (ContextCompat.checkSelfPermission(CNIC.this, Manifest.permission.CAMERA)== PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bitmap bitmap;

                if(seelected==1)
                {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    cnicFront.setImageBitmap(bitmap);

//                    Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
//                    bitmap=getResizedBitmap(bitmap,800);
                    cnic_front_file=new File(String.valueOf(BitmapToFile(CNIC.this,bitmap,"cnic_front.jpg")));


                }
                else if(seelected==2)
                {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    cnicBack.setImageBitmap(bitmap);
//                    Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
//                    bitmap=getResizedBitmap(bitmap,800);
                    cnic_back_file=new File(String.valueOf(BitmapToFile(CNIC.this,bitmap,"cnic_back.jpg")));
                }

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImage = data.getData();
                if(seelected==1)
                {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cnicFront.setImageBitmap(bitmap);
                    cnic_front_file=new File(String.valueOf(BitmapToFile(CNIC.this,bitmap,"cnic_front.jpg")));

                }
                else if(seelected==2)
                {
                    Bitmap bitmap = null;

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cnicBack.setImageBitmap(bitmap);
                    cnic_back_file=new File(String.valueOf(BitmapToFile(CNIC.this,bitmap,"cnic_back.jpg")));
                }
            }
        }

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












}
