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
        TextView accountstatus=findViewById(R.id.accountno);
        TextView udisestatus=findViewById(R.id.udisecode);
        TextView admissionstatus=findViewById(R.id.admissionno);
        TextView classstatus=findViewById(R.id.classthird);
        TextView complaintid=findViewById(R.id.idStatus);
        TextView childnamestatus=findViewById(R.id.namestatus);

        DatabaseReference compref=(ref.child("Complaints").child(spid));
        DatabaseReference bankref=(ref.child("Bank").child("Complaints").child(spid));
        DatabaseReference ageref=(ref.child("UIDAI").child("Complaints").child(spid));
        DatabaseReference sortref=(ref.child("Complaints").child(spid));
        DatabaseReference edustatusref=(ref.child("Education").child("Complaints").child(spid));
        DatabaseReference statuscompref=(ref.child("Complaints").child(spid).child("status"));

        compref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String compstat=String.valueOf(snapshot.child("complaintID").getValue());
                complaintid.setText(compstat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sortref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sortstat=String.valueOf(snapshot.child("sort").getValue());
                sortstatus.setText(sortstat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ageref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String agestat=String.valueOf(snapshot.child("age").getValue());
                agestatus.setText(agestat);
                String namestat=String.valueOf(snapshot.child("name").getValue());
                namestatus.setText(namestat);
                childnamestatus.setText(namestat);
                String aadharstat=String.valueOf(snapshot.child("aadhar").getValue());
                aadharstatus.setText(aadharstat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bankref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String ifsstat=String.valueOf(snapshot.child("ifsc").getValue());
                    ifsstatus.setText(ifsstat);

                    String accountstat=String.valueOf(snapshot.child("accountno").getValue());
                    accountstatus.setText(accountstat);

                    String bank=String.valueOf(snapshot.child("status").getValue());
                    String education=String.valueOf(snapshot.child("status").getValue());

                if(bank.equals("1"))
            currentstatus.setText("Bank");
        else{
            if(education.equals("1"))
                currentstatus.setText("Education");
            else
                currentstatus.setText("UIDAI");
        }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edustatusref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String udisestat=String.valueOf(snapshot.child("udise").getValue());
                udisestatus.setText(udisestat);

                String admissionstat=String.valueOf(snapshot.child("admissionno").getValue());
                admissionstatus.setText(admissionstat);

                String classstat=String.valueOf(snapshot.child("grade").getValue());
                classstatus.setText(classstat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        if(bank.equals("1"))
//            currentstatus.setText("Bank");
//        else{
//            if(education.equals("1"))
//                currentstatus.setText("Education");
//            else
//                currentstatus.setText("UIDAI");
//        }


    }
}