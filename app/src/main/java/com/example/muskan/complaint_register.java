package com.example.muskan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class complaint_register extends AppCompatActivity {


    private static  final int REQUEST_LOCATION=11;
    private long complaintID;
    private int k;
    private FirebaseDatabase database;
    private String tasklabel, name, age, uril, urilsec,typelabel;
    private EditText nameedit, ageedit;
    private Spinner spinner;
    private Spinner labourtype;
    private final int CAMERA_PICTURE_REQUEST_CODE = 20;
    private Uri filePath;
    private final int PICK_IMAGE_GALLERY_CODE = 78;
    private ImageView imagePreviw, continuebtn;
    private Button capture;
    private FirebaseAuth mauth;
    private FirebaseUser user;
    private LocationManager locationManager;


    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1;


    private String[] tasks = {"Hazardous", "Non-Hazardous"};
    private String[] types={"Slave", "Trafficked child", "Debt bonded", "Forced labour"};
    private String Latitude;
    private String Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_register);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        complaintID = prefs.getLong("COMPLAINT_ID", 25200100);
        SharedPreferences prefsk = getPreferences(MODE_PRIVATE);
        k=prefsk.getInt("k", 101);
        complaintID++;
        k++;

        SharedPreferences.Editor editork = getPreferences(MODE_PRIVATE).edit();
        editork.putInt("k",k);
        editork.apply();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putLong("COMPLAINT_ID", complaintID);
        editor.apply();

        spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this
                , android.R.layout.simple_spinner_item, tasks);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tasklabel = tasks[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        labourtype=findViewById(R.id.labourtype);
        ArrayAdapter adapter1=new ArrayAdapter(this, android.R.layout.simple_spinner_item,types);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labourtype.setAdapter(adapter1);
        labourtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typelabel=types[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nameedit = findViewById(R.id.nameedit1);
        ageedit = findViewById(R.id.ageedit1);

        imagePreviw = findViewById(R.id.imagepreview);
        database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://muskan-cba1b.appspot.com");
        databaseReference = database.getReference();
        storageReference = firebaseStorage.getReference();
        capture = findViewById(R.id.capture);
        continuebtn = findViewById(R.id.continueButton);
        ActivityCompat.requestPermissions(complaint_register.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                }
                uploadImage();
                getLocation();
                dataInFirebase();





//                Intent intent=new Intent(complaint_register.this,regiesteredComplaints.class);
//                startActivity(intent);

            }
        });



        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageSelectedDialog();
            }
        });


    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(complaint_register.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(complaint_register.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                Latitude=String.valueOf(lat);
                Longitude=String.valueOf(longi);


            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                Latitude=String.valueOf(lat);
                Longitude=String.valueOf(longi);


            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                Latitude=String.valueOf(lat);
                Longitude=String.valueOf(longi);


            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }



    }


    private void uploadImage() {

        if(filePath != null) {



            StorageReference ref = storageReference.child("images/image"+k);



            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            uril=uri.toString();

                            databaseReference.child("Users").child(user.getUid()).child("Complaints").child(String.valueOf(complaintID)).child("image").setValue(uri.toString());
                            databaseReference.child("Complaints").child(String.valueOf(complaintID)).child("image").setValue(uri.toString());
                            Toast.makeText(complaint_register.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(complaint_register.this, "Image uploaded failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }


    private void dataInFirebase() {

        name=nameedit.getText().toString();
        age=ageedit.getText().toString();
        mauth=FirebaseAuth.getInstance();
        user= mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com");
        DatabaseReference ref=database.getReference();

        complaintDataHolder complaint=new complaintDataHolder(uril.toString(),name.toString(),age.toString(),tasklabel.toString(),complaintID,Latitude.toString(),Longitude.toString(),typelabel.toString());
        ref.child("Users").child(user.getUid()).child("Complaints").child(String.valueOf(complaintID)).setValue(complaint);
        ref.child("Complaints").child(String.valueOf(complaintID)).setValue(complaint);
        ref.child("Complaints").child(String.valueOf(complaintID)).child("image").setValue(urilsec);
        ref.child("Complaints").child(String.valueOf(complaintID)).child("uid").setValue((user.getUid()));
    }

    private void showImageSelectedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select app");
        builder.setMessage("Please select an option");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCameraPermission();
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectFromGallery();
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(complaint_register.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(complaint_register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(complaint_register.this, new String[] {
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[1] ==PackageManager.PERMISSION_GRANTED){
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_PICTURE_REQUEST_CODE);
        }
    }



    private void selectFromGallery(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  ==  PICK_IMAGE_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            if(data == null || data.getData() == null)
                return;

            try {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagePreviw.setImageBitmap(bitmap);
            }catch (Exception e) {

            }
        } else if(requestCode == CAMERA_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap  = (Bitmap)extras.get("data");
            imagePreviw.setImageBitmap(bitmap);
            filePath =getImageUri(getApplicationContext(), bitmap);
        }

    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }
}

