package com.example.muskan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class status extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Intent intent=getIntent();
        FirebaseDatabase db=FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();

        String spid=intent.getStringExtra("empId");

        TextView namestatus=findViewById(R.id.chaman);
        TextView agestatus=findViewById(R.id.twelve);
        TextView sortstatus=findViewById(R.id.hazard);
        TextView currentstatus=findViewById(R.id.dept);
        TextView aadharstatus=findViewById(R.id.aadharid);
        TextView ifsstatus=findViewById(R.id.ifsc);
        TextView accountstatus=findViewById(R.id.udisecode);
        TextView udisestatus=findViewById(R.id.accountno);
        TextView admissionstatus=findViewById(R.id.admissionno);
        TextView classstatus=findViewById(R.id.classthird);
        TextView complaintid=findViewById(R.id.idStatus);
        TextView childnamestatus=findViewById(R.id.namestatus);

        DatabaseReference nameref=(ref.child("UIDAI").child("Complaints").child(spid).child("name"));
        DatabaseReference ageref=(ref.child("UIDAI").child("Complaints").child(spid).child("age"));
        DatabaseReference sortref=(ref.child("Complaints").child(spid).child("sort")));
        DatabaseReference edustatusref=(ref.child("Education").child("Complaints").child(spid).child("status"));
        DatabaseReference statuscompref=(ref.child("Complaints").child(spid).child("status"));

        nameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namestat=snapshot.toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

//        if(bank.equals("1"))
//            currentstatus.setText("Bank");
//        else{
//            if(education.equals("1"))
//                currentstatus.setText("Education");
//            else
//                currentstatus.setText("UIDAI");
//        }

        aadharstatus.setText(String.valueOf(ref.child("UIDAI").child("Complaints").child(spid).child("aadhar").get()));
        ifsstatus.setText(String.valueOf(ref.child("Bank").child("Complaints").child(spid).child("ifsc").get()));
        accountstatus.setText(String.valueOf(ref.child("Bank").child("Complaints").child(spid).child("account").get()));
        udisestatus.setText(String.valueOf(ref.child("Bank").child("Complaints").child(spid).child("udise").get()));
        admissionstatus.setText(String.valueOf(ref.child("Education").child("Complaints").child(spid).child("admission").get()));
        classstatus.setText(String.valueOf(ref.child("Education").child("Complaints").child(spid).child("class").get()));
        namestatus.setText(String.valueOf(ref.child("UIDAI").child("Complaints").child(spid).child("name").get()));
    }
}