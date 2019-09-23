package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditTips extends AppCompatActivity {

    private Button confirm;
    private EditText title,description;
    private ImageButton nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tips);
        confirm = findViewById(R.id.btnConfirmEditTips);
        title = findViewById(R.id.txtTitle);
        description =  findViewById(R.id.txtDesc);

        nav_back = (ImageButton) findViewById(R.id.ic_nav_back_tips);

        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(EditTips.this, MainActivity.class);
                main.putExtra("from","tips");
                startActivity(main);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
