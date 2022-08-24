package com.example.muskan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class regiesteredComplaints extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mauth;
    FirebaseUser user;
    RecyclerView recyc;
    complaintAdapter adapter;
    ArrayList<Datamodel> listComp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiestered_complaints);
        recyc = findViewById(R.id.idRVComplaints);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyc.setLayoutManager(linearLayoutManager);
        listComp= new ArrayList<>();

        adapter= new complaintAdapter(this,listComp);
        recyc.setAdapter(adapter);

        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");
        ref=database.getReference().child("Users").child(user.getUid()).child("Complaints");
     //   https://muskan-cba1b-default-rtdb.firebaseio.com/


        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Datamodel datamodel= dataSnapshot.getValue(Datamodel.class);
                    listComp.add(datamodel);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



}}