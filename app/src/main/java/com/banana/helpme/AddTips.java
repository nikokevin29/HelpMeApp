package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AddTips extends AppCompatActivity {

    private EditText title, description;
    private ImageButton NavBack;
    private Button btnShare;
    private LinearLayout btnCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        NavBack = (ImageButton) findViewById(R.id.ic_nav_back_tips);
        title = (EditText) findViewById(R.id.txtTitle);
        description = (EditText) findViewById(R.id.txtDesc);
        btnCamera = (LinearLayout) findViewById(R.id.btnCamera);
        btnShare = (Button) findViewById(R.id.btnShare);

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ad = new Intent(AddTips.this, MainActivity.class);
                ad.putExtra("from","AddPost");
                startActivity(ad);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(AddTips.this, MainActivity.class);
                startActivity(share);
            }
        });
    }
}
