package com.example.digitalcontracts.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalcontracts.Models.PDF;
import com.example.digitalcontracts.R;

import java.util.ArrayList;

public class PDF_ADAPTER extends RecyclerView.Adapter<PDF_ADAPTER.PDF_ADAPTER_VIEW_HOLDER> {
    private ProgressDialog loadingBar;

    private Context mcontext;
    ArrayList<PDF> data;
    public PDF_ADAPTER(ArrayList<PDF> data, Context context)
    {
        mcontext=context;
        this.data=data;
    }

    @NonNull
    @Override
    public PDF_ADAPTER_VIEW_HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_pdf_view,parent,false);


        return new PDF_ADAPTER_VIEW_HOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PDF_ADAPTER_VIEW_HOLDER holder, final int position) {
        holder.name.setText(data.get(position).getName().toString());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(mcontext, com.example.digitalcontracts.PDF.class);
                intent1.putExtra("url",data.get(position).getDoc().toString());
                intent1.putExtra("name",data.get(position).getName().toString());
                mcontext.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PDF_ADAPTER_VIEW_HOLDER extends RecyclerView.ViewHolder{
TextView name;
        public PDF_ADAPTER_VIEW_HOLDER(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);

        }
    }
}
