package com.example.muskan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class status extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Intent intent=getIntent();
        FirebaseDatabase db=FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();

        String spid=intent.getStringExtra("empId");
        Toast.makeText(this,"complaint id"+spid,Toast.LENGTH_SHORT).show();
    }
}