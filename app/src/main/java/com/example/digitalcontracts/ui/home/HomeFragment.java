package com.example.digitalcontracts.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.digitalcontracts.Adapters.Dashboard_adapter;
import com.example.digitalcontracts.Models.Dashboard_api;
import com.example.digitalcontracts.Models.Result;
import com.example.digitalcontracts.R;
import com.example.digitalcontracts.Retrofit_Client_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView applications;
    private ProgressDialog loadingBar;
ImageView imageView;
TextView name,city;
    private HomeViewModel homeViewModel;
    SwipeRefreshLayout pullToRefresh1,pullToRefresh2,pullToRefresh3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        loadingBar=new ProgressDialog(getContext());
        imageView=root.findViewById(R.id.profile_image);
        name=root.findViewById(R.id.name);
        pullToRefresh2=root.findViewById(R.id.pullToRefresh2);

        city=root.findViewById(R.id.city);
        applications=root.findViewById(R.id.applications);
        applications.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
setvalues();

        pullToRefresh2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setvalues();
                pullToRefresh2.setRefreshing(false);
            }
        });
        return root;
    }
    private void setvalues() {
//        loadingBar.setTitle("Logging In");
        loadingBar.setMessage("Please Wait!");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Shared", Context.MODE_PRIVATE);

        Call<Dashboard_api> call = Retrofit_Client_URL.getInstance().getapi().dashboard(sharedPreferences.getString("Token","1111"));

        call.enqueue(new Callback<Dashboard_api>() {
                         @Override
                         public void onResponse(@NonNull Call<Dashboard_api> call, @NonNull Response<Dashboard_api> response) {

                             if(response.code()==401)
                             {
                                 loadingBar.cancel();

                                 Toast.makeText(getContext(), "Unauthorized", Toast.LENGTH_SHORT).show();
                             }else if(response.code()==200) {
                                 try {
                                     name.setText(response.body().getUserData().get(0).getName());
                                     city.setText(response.body().getUserData().get(0).getCity());

                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                               try {
                                   URL url = new URL(Retrofit_Client_URL.BASE_URL.replace("/api","") +response.body().getUserData().get(0).getProfileImage());

                                   Glide.with(getContext()).load(url).centerCrop().into(imageView);
                               } catch (MalformedURLException e) {
                                   e.printStackTrace();
                               }
                                 ArrayList<Result> arrayList=new ArrayList<>();
                                 arrayList= (ArrayList<Result>) response.body().getResult();
                                 applications.setAdapter(new Dashboard_adapter(arrayList,getContext()));

                                 loadingBar.cancel();

                             }}

                         @Override
                         public void onFailure(Call<Dashboard_api> call, Throwable t) {
                             loadingBar.cancel();
                             Toast.makeText(getContext(), "Failed to hit API "+t, Toast.LENGTH_SHORT).show();
                         }

                     }
        );

    }
}
