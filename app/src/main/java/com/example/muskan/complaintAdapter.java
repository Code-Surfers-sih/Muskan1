package com.example.muskan;

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

public class complaintAdapter extends FirebaseRecyclerAdapter<model,complaintAdapter.myviewholder> {

    public complaintAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_rv_item,parent,false);
        return new myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.name.setText(model.getName());
        holder.age.setText(model.getAge());
        holder.type.setText(model.getSort());
        holder.id.setText((int) model.getComplaintID());
        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);
    }



    static class myviewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,age,type,id;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.imagerv);
            name=(TextView) itemView.findViewById(R.id.namerv);
            age=(TextView) itemView.findViewById(R.id.age);
            type=(TextView) itemView.findViewById(R.id.typerv);
            id=(TextView) itemView.findViewById(R.id.idRVComplaints);

        }
    }
}
