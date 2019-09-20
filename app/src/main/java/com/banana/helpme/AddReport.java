package com.banana.helpme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private String photoData;
    final int cameraCode = 99;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

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

        onLocationChanged(location);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == cameraCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            camera.setImageBitmap(bitmap);
            photoData = BitMapToString(bitmap);
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void onLocationChanged(final Location location)
    {
        double longitude = location.getLongitude();
        double latitude =  location.getLatitude();

        Toast.makeText(this, "Lat : " + latitude + " Long : " + longitude, Toast.LENGTH_SHORT).show();
        //getAddress(latitude,longitude);
        String alamat = getAddress(AddReport.this,latitude,longitude);
        tvLocation.setText(alamat);
        //Toast.makeText(AddReport.this,"alamat"+alamat,Toast.LENGTH_LONG).show(); //tampilin toast
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
            Log.e("errror get address", e.getMessage());
        }
        return alamat;
    }

    private String getSysDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
