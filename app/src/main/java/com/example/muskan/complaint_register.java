package com.example.muskan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class complaint_register<record> extends AppCompatActivity {


    private static final int VOICE_REQUEST=52;
    private static  final int REQUEST_LOCATION=11;
    private long complaintID;
    private int k;
    private FirebaseDatabase database;
    private String tasklabel, name, age, uril, urilsec,typelabel,urilfirst;
    private EditText nameedit, ageedit;
    private Spinner spinner;
    private Spinner labourtype;
    private final int CAMERA_PICTURE_REQUEST_CODE = 20;
    private Uri filePath;
    private final int PICK_IMAGE_GALLERY_CODE = 78;
    private ImageView imagePreviw;
    private Button capture,continuebtn,record;
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
    Member member;

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

        spinner = findViewById(R.id.spinnerregi);
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

        labourtype=findViewById(R.id.labourtyperegi);
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


        nameedit = findViewById(R.id.nameedit1regi);
        ageedit = findViewById(R.id.ageedit1regi);

        record=findViewById(R.id.record);
        imagePreviw = findViewById(R.id.imagepreview);
        database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://muskan-cba1b.appspot.com");
        databaseReference = database.getReference();
        storageReference = firebaseStorage.getReference();
        capture = findViewById(R.id.captureregi);
        continuebtn = findViewById(R.id.continueButtonregi);
        ActivityCompat.requestPermissions(complaint_register.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database=FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com/");
                DatabaseReference ref=database.getReference().child("Complaints");
                ref.child("panic").setValue("1");

            }

            });





        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps

                    OnGPS();
                } else {
                    //GPS is already On then

                    getLocation();
                }
                uploadImage();
                getLocation();
                dataInFirebase();
                database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com");
                DatabaseReference ref=database.getReference();


                DatabaseReference refcomp=ref.child("Users").child(user.getUid());
                refcomp.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        notification();
                    }



                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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
                            urilfirst=uri.toString();
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
        String imgurl=uril;
        name=nameedit.getText().toString();
        age=ageedit.getText().toString();
        mauth=FirebaseAuth.getInstance();
        user= mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance("https://muskan-cba1b-default-rtdb.firebaseio.com");
        DatabaseReference ref=database.getReference();
        complaintDataHolder complaint=new complaintDataHolder(name,age,tasklabel,complaintID,Latitude,Longitude);

        DatabaseReference refcomp=ref.child("Users").child(user.getUid()).child("Complaints").child(String.valueOf(complaintID));
        refcomp.setValue(complaint);
        refcomp.child("image").setValue(uril);

         ref.child("Users").child(user.getUid()).child("Complaints").child(String.valueOf(complaintID)).child("typeofwork").setValue(typelabel);
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
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
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

        if (requestCode == VOICE_REQUEST && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
        }

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
            assert data != null;
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

    private void notification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }


        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n").setContentText("Muskan")
                .setSmallIcon(R.id.logo)
                .setAutoCancel(true)
                .setContentText("New Complaint is registered");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }


}

