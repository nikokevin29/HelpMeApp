package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EditAccount extends AppCompatActivity {

    private Button Confirm;
    private ImageButton NavBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        NavBack = (ImageButton) findViewById(R.id.ic_nav_back_acc);
        Confirm = (Button) findViewById(R.id.btnConfirm);

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nav = new Intent(EditAccount.this, AccountFragment.class);
                startActivity(nav);
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent con = new Intent(EditAccount.this, AccountFragment.class);
                startActivity(con);
            }
        });
    }
}
