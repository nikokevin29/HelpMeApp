package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AddTips extends AppCompatActivity {

    private ImageButton NavBack;
    private Button Share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        NavBack = (ImageButton) findViewById(R.id.ic_nav_back_tips);
        Share = (Button) findViewById(R.id.btnShare);

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ad = new Intent(AddTips.this, AddFragment.class);
                startActivity(ad);
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(AddTips.this, MainActivity.class);
                startActivity(share);
            }
        });
    }
}
