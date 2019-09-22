package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTips extends AppCompatActivity {

    private Button confirm;
    private EditText title,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tips);
        confirm = findViewById(R.id.btnConfirmEditTips);
        title = findViewById(R.id.txtTitle);
        description =  findViewById(R.id.txtDesc);



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
