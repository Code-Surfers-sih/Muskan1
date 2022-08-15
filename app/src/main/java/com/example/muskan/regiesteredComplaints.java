package com.example.muskan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class regiesteredComplaints extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mauth;
    FirebaseUser user;
    RecyclerView recyc;
    complaintAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiestered_complaints);
        recyc = findViewById(R.id.idRVComplaints);
        recyc.setLayoutManager(new LinearLayoutManager(this));

        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(database.getReference("Users").child(user.getUid()).child("Complaints"), model.class)
                        .build();

        adapter = new complaintAdapter(options);
        recyc.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}