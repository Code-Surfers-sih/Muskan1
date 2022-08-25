package com.example.muskan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    private Button button,signUp;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private EditText regusername,regemail, regphoneno, regpassword;
    FirebaseAuth mauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button=findViewById(R.id.option);
        signUp=findViewById(R.id.continueButton);


        regusername=findViewById(R.id.usernameedit);
        regemail=findViewById(R.id.emailedit);
        regphoneno=findViewById(R.id.phoneedit);
        regpassword=findViewById(R.id.passwordedit);
        mauth=FirebaseAuth.getInstance();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity2.this,login_screen.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=regusername.getText().toString();
                String email=regemail.getText().toString();
                String phone=regphoneno.getText().toString();
                String password=regpassword.getText().toString();

                if(username.isEmpty()){
                    regusername.setError("user name is required");
                    regusername.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    regemail.setError("email is required");
                    regemail.requestFocus();
                    return;
                }

                if(phone.isEmpty()){
                    regphoneno.setError("Phone number is required");
                    regphoneno.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    regpassword.setError("Password is required");
                    regpassword.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    regemail.setError("Please provide valid email address!");
                    regemail.requestFocus();
                    return;
                }


                if(password.length()<6)
                {
                    regpassword.setError("Password must not be less than 6 characters");
                    regpassword.requestFocus();
                    return;
                }




                mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            rootNode=FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com");
                            reference=rootNode.getReference("Users");
                            UserHelperClass addNewUser= new UserHelperClass(username,email,phone,password);
                            reference.child(Objects.requireNonNull(mauth.getUid())).setValue(addNewUser);
                            Toast.makeText(MainActivity2.this, "User Registered", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity2.this, login_screen.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity2.this, "failed to register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

        });


        }

    }
