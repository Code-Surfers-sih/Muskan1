package com.example.muskan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login_screen extends AppCompatActivity {

    private EditText usernameedit, passwordedit;
    private ImageView signin;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);



        usernameedit=findViewById(R.id.usernameeditlogin);
        passwordedit=findViewById(R.id.passwordeditlogin);
        signin=findViewById(R.id.signin_button);
        TextView noAccount=findViewById(R.id.noAccount);





        mauth=FirebaseAuth.getInstance();

       signin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String email=usernameedit.getText().toString();
               String password=passwordedit.getText().toString();

               if (TextUtils.isEmpty(email)) {
                   Toast.makeText(login_screen.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();

               }

               if (TextUtils.isEmpty(password)) {
                   Toast.makeText(login_screen.this, "Enter a password", Toast.LENGTH_SHORT).show();

               }
               else{
               mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(login_screen.this, "Login successful...", Toast.LENGTH_SHORT).show();
                           FirebaseUser user=mauth.getCurrentUser();
                           Intent intent=new Intent(login_screen.this, accepted_complaints.class);
                           startActivity(intent);



                       }
                       else
                           Toast.makeText(login_screen.this, "Failed to Login"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }}
       });

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            }
        });



}}