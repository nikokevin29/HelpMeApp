package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class EditReport extends AppCompatActivity {

    private LinearLayout camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);
        camera = (LinearLayout) findViewById(R.id.btnCamera);
    }
}
