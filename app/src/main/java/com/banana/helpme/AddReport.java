package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddReport extends AppCompatActivity {
    private ImageButton NavBack;
    private Spinner category;
    private LinearLayout btnCamera, btnLocation;
    private EditText description;
    private Button btnPost;

    private Image image;
    private float longitude, latitude;

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

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad = new Intent(AddReport.this, MainActivity.class);
                ad.putExtra("from","AddPost");
                startActivity(ad);
            }
        });
    }
}
