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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class accepted_complaints extends AppCompatActivity {


    FirebaseDatabase databaseacc;
    DatabaseReference refacc;
    FirebaseAuth mauthacc;
    FirebaseUser useracc;
    RecyclerView recycacc;
    acceptedAdapter adapteracc;
    ArrayList<Modelacc> listCompacc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_complaints);
        recycacc = findViewById(R.id.idRVAccepted);
        LinearLayoutManager linearLayoutManageracc=new LinearLayoutManager(this);
        recycacc.setLayoutManager(linearLayoutManageracc);
        listCompacc= new ArrayList<Modelacc>();

        adapteracc= new acceptedAdapter(this,listCompacc);
        recycacc.setAdapter(adapteracc);

        mauthacc = FirebaseAuth.getInstance();
        useracc = mauthacc.getCurrentUser();
        databaseacc = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");
        refacc=databaseacc.getReference().child("Users").child(useracc.getUid()).child("Complaints");
        //   https://muskan-cba1b-default-rtdb.firebaseio.com/
        Query query=refacc.orderByChild("status").equalTo("1");

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotacc) {
                for(DataSnapshot dataSnapshotacc:snapshotacc.getChildren())
                {
                    Modelacc modelacc= dataSnapshotacc.getValue(Modelacc.class);
                    listCompacc.add(modelacc);
                }
                adapteracc.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }}