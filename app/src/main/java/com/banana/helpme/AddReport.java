package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.ReportDAO;
import com.banana.helpme.UserData.UserDAO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReport extends AppCompatActivity {
    private ImageButton NavBack;
    private Spinner category;
    private LinearLayout btnCamera, btnLocation;
    private TextView tvLocation, tvPicture;
    private EditText description;
    private Button btnPost;

    private ImageView camera;
    private LocationManager locationManager;
    private double latitude, longitude;

    final int cameraCode = 99;
    private String alamat, username;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    // Create a storage reference from our app
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    // Create a reference to "photo-report"
    StorageReference ReportRef = storageRef.child("photo-report/");
    public Uri imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        NavBack = (ImageButton) findViewById(R.id.ic_nav_back);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        btnCamera = (LinearLayout) findViewById(R.id.btnPicture);
        btnLocation = (LinearLayout) findViewById(R.id.btnLocation);
        description = (EditText) findViewById(R.id.txtDesc);
        btnPost = (Button) findViewById(R.id.btnPost);

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvPicture = (TextView) findViewById(R.id.pic);
        camera = (ImageView) findViewById(R.id.camera);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad = new Intent(AddReport.this, MainActivity.class);
                ad.putExtra("from", "AddPost");
                startActivity(ad);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        final Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, cameraCode);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLocationChanged(location);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReport();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == cameraCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            camera.setImageBitmap(bitmap);
        }
    }

    public void onLocationChanged(final Location location)
    {
        double longitude = location.getLongitude();
        double latitude =  location.getLatitude();

        Toast.makeText(this, "Lat : " + latitude + " Long : " + longitude, Toast.LENGTH_SHORT).show();
        //getAddress(latitude,longitude);
        alamat = getAddress(AddReport.this,latitude,longitude);
        tvLocation.setText(alamat);
    }
    private String getAddress(Context c, double latitude, double longitude) {
        String alamat =null;
        try {
            Geocoder geocoder = new Geocoder(c, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                alamat = address.getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e("error get address", e.getMessage());
        }
        return alamat;
    }

    private String getSysDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void createReport(){
        System.out.println(user.getEmail());
        uploadImage();
    }

    public void uploadImage(){
        // Get the data from an ImageView as bytes
        System.out.println("BANANA "+storageRef);
        System.out.println("BANANA "+ReportRef);
        camera.setDrawingCacheEnabled(true);
        camera.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) camera.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final StorageReference PhotoRef = ReportRef.child("img"+getSysDate());
        final UploadTask uploadTask = PhotoRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(AddReport.this, "Problem Uploading Photo", Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Task<Uri> url = taskSnapshot.getStorage().getDownloadUrl();
                while(!url.isSuccessful());
                Uri downloadURL = url.getResult();
                imageURL = downloadURL;
                System.out.println("PISANG URL "+downloadURL);
                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                Call<List<UserDAO>> userDAOCall = apiService.getAllUser();
                userDAOCall.enqueue(new Callback<List<UserDAO>>() {
                    @Override
                    public void onResponse(Call<List<UserDAO>> call, Response<List<UserDAO>> response) {
                        for (int i=0; i<response.body().size(); i++){
                            if(response.body().get(i).getEmail().equals(user.getEmail())){
                                username = response.body().get(i).getUsername();
                                System.out.println(username);
                                String kategori = category.getSelectedItem().toString();
                                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                                System.out.println("LINK FOTO "+imageURL);
                                Call<ReportDAO> reportDAOCall = apiService.addReport(kategori, imageURL.toString(), alamat,
                                        description.getText().toString(), username, getSysDate());
                                reportDAOCall.enqueue(new Callback<ReportDAO>() {
                                    @Override
                                    public void onResponse(Call<ReportDAO> call, Response<ReportDAO> response) {
                                        Toast.makeText(AddReport.this, "Add Report Success", Toast.LENGTH_SHORT).show();
                                        Intent post = new Intent(AddReport.this, MainActivity.class);
                                        startActivity(post);
                                    }

                                    @Override
                                    public void onFailure(Call<ReportDAO> call, Throwable t) {
                                        Toast.makeText(AddReport.this, "Add Report Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<UserDAO>> call, Throwable t) {
                        System.out.println("gagal");
                    }
                });

            }
        });
    }

}
