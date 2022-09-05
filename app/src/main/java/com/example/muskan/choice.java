package com.example.muskan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Button registerbtnchoice=findViewById(R.id.registerbtnchoice);
        Button registeredbtnchoice=findViewById(R.id.registeredbtnchoice);
        Button acceptedbtnchoice=findViewById(R.id.acceptedbtnchoice);

        TextView Tobechanged=findViewById(R.id.tobechanged);
        FirebaseDatabase database=FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");
        DatabaseReference ref=database.getReference("Users");
        FirebaseAuth mauth= FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                assert user != null;
                String tobechanged=String.valueOf(snapshot.child(user.getUid()).child("username").getValue());
                Tobechanged.setText(tobechanged);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        acceptedbtnchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(choice.this, accepted_complaints.class);
                startActivity(intent);
            }
        });

        registerbtnchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(choice.this, complaint_register.class);
                startActivity(intent);
            }
        });

        registeredbtnchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(choice.this, regiesteredComplaints.class);
                startActivity(intent);
            }
        });

    }
}