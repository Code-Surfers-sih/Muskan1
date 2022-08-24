package com.example.muskan;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class acceptedAdapter extends RecyclerView.Adapter<acceptedAdapter.myViewHolder>{

    Context context;
    ArrayList<Modelacc> list;

    public acceptedAdapter(Context context, ArrayList<Modelacc> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Modelacc datamodel= list.get(position);
        holder.namevh.setText(String.valueOf(datamodel.getName()));
        holder.agevh.setText(String.valueOf(datamodel.getAge()));
        holder.sortvh.setText(String.valueOf(datamodel.getSort()));
        holder.complaintvh.setText(String.valueOf(datamodel.getComplaintID()));
        Glide.with(holder.iv.getContext()).load(String.valueOf(datamodel.getImage())).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, status.class);
                intent.putExtra("empId", String.valueOf(datamodel.getComplaintID()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView namevh, agevh, sortvh, complaintvh;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            namevh= itemView.findViewById(R.id.namerv);
            agevh= itemView.findViewById((R.id.agerv));
            sortvh= itemView.findViewById(R.id.typerv);
            iv= itemView.findViewById(R.id.imagerv);
            complaintvh= itemView.findViewById(R.id.complaintIDrv);
        }
    }
}