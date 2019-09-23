package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.banana.helpme.UserData.TipsDAO;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    String stringImg, username;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    // Create a storage reference from our app
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    // Create a reference to "photo-report"
    StorageReference ReportRef = storageRef.child("photo-tips/");
    public Uri imageURL;

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
                postTips();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSysDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void postTips(){
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
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                System.out.println(user.getEmail());
                Call<List<UserDAO>> userDAOCall = apiService.getAllUser();
                userDAOCall.enqueue(new Callback<List<UserDAO>>() {
                    @Override
                    public void onResponse(Call<List<UserDAO>> call, Response<List<UserDAO>> response) {
                        for (int i=0; i<response.body().size(); i++){
                            if(response.body().get(i).getEmail().equals(user.getEmail())){
                                username = response.body().get(i).getUsername();
                                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                                Call<TipsDAO> tipsDAOcall = apiService.addTips(title.getText().toString(),
                                        description.getText().toString(), imageURL.toString(), username, getSysDate());
                                tipsDAOcall.enqueue(new Callback<TipsDAO>() {
                                    @Override
                                    public void onResponse(Call<TipsDAO> call, Response<TipsDAO> response) {
                                        Toast.makeText(AddTips.this, "success", Toast.LENGTH_SHORT).show();
                                        Intent share = new Intent(AddTips.this, MainActivity.class);
                                        share.putExtra("from","tips");
                                        startActivity(share);
                                    }

                                    @Override
                                    public void onFailure(Call<TipsDAO> call, Throwable t) {
                                        Toast.makeText(AddTips.this, "failed", Toast.LENGTH_SHORT).show();
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTips.this, "Problem Uploading Photo", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
