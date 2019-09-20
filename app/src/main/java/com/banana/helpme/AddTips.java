package com.banana.helpme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTips extends AppCompatActivity {

    private EditText title, description;
    private ImageButton NavBack;
    private ImageView camera;
    private Button btnShare;
    private LinearLayout btnCamera;

    final int galleryCode = 100;
    Uri imageUri;
    Bitmap bitmapImg;
    String stringImg;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        NavBack = (ImageButton) findViewById(R.id.ic_nav_back_tips);
        title = (EditText) findViewById(R.id.txtTitle);
        description = (EditText) findViewById(R.id.txtDesc);
        btnCamera = (LinearLayout) findViewById(R.id.btnCamera);
        btnShare = (Button) findViewById(R.id.btnShare);
        camera = (ImageView) findViewById(R.id.cameraLogo);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ad = new Intent(AddTips.this, MainActivity.class);
                ad.putExtra("from","AddPost");
                startActivity(ad);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, galleryCode);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                Call<String> tipsDAOcall = apiService.addTips(title.getText().toString(),
                        description.getText().toString(), bitmapImg.toString(), "pradnya123", getSysDate());
                tipsDAOcall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(AddTips.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(AddTips.this, "failed to share", Toast.LENGTH_SHORT).show();
                        Intent share = new Intent(AddTips.this, MainActivity.class);
                        startActivity(share);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == galleryCode && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                bitmapImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                camera.setImageBitmap(bitmapImg);
                stringImg = BitMapToString(bitmapImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private String getSysDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
