package com.example.digitalcontracts.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalcontracts.MainActivity;
import com.example.digitalcontracts.Models.Result;
import com.example.digitalcontracts.R;
import com.example.digitalcontracts.Retrofit_Client_URL;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Dashboard_adapter extends RecyclerView.Adapter<Dashboard_adapter.Dashboard_adapter_VIEW_HOLDER> {

    private ProgressDialog loadingBar;

    private Context mcontext;
    ArrayList<Result> data;
    public Dashboard_adapter(ArrayList<Result> data, Context context)
    {
        mcontext=context;
        this.data=data;
    }
    @NonNull
    @Override
    public Dashboard_adapter_VIEW_HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_home,parent,false);


        return new Dashboard_adapter_VIEW_HOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Dashboard_adapter_VIEW_HOLDER holder, final int position) {
        holder.name.setText("Name: "+data.get(position).getName().toString());
        holder.id.setText("ID: "+data.get(position).getApplicationId().toString());
        holder.dos.setText("DOS: "+data.get(position).getDOS().toString());


        try {
            URL url = new URL(Retrofit_Client_URL.BASE_URL.replace("/api","") +data.get(position).getProfileImage());

            Glide.with(mcontext).load(url).into(holder.imageView);
        } catch(IOException e) {
            System.out.println(e);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, MainActivity.class);
                intent.putExtra("id",data.get(0).getApplicationId().toString());
                intent.putExtra("status",data.get(position).getApplicantStatus().toString());
//                Toast.makeText(mcontext, ""+data.get(0).getApplicationId().toString(), Toast.LENGTH_SHORT).show();
                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Dashboard_adapter_VIEW_HOLDER extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,id,dos;
        public Dashboard_adapter_VIEW_HOLDER(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
            dos=itemView.findViewById(R.id.dos);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }
}
