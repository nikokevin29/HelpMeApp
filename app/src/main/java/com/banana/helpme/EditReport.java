package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class EditReport extends AppCompatActivity {

    private LinearLayout camera;
    private Button confirm;
    Spinner  category;
    private EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);
        camera = (LinearLayout) findViewById(R.id.btnCamera);
        confirm = (Button) findViewById(R.id.btnConfirmEditReport);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        description = (EditText) findViewById(R.id.etDescription);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
