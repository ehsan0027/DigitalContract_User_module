package com.example.digitalcontracts;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Client_URL {
    public static final String BASE_URL="http://82.27.54.209:5000/api/";
    public static Retrofit_Client_URL minstance;
    public static Retrofit retrofit;

    Gson gson=new GsonBuilder().setLenient().create();


    private Retrofit_Client_URL(){


        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

    }
    public static synchronized Retrofit_Client_URL getInstance(){
        if (minstance== null) {
            minstance= new Retrofit_Client_URL();
        }
        return minstance;
    }

    public Retrofit_Api_class getapi(){
        return retrofit.create(Retrofit_Api_class.class);
    }

}
