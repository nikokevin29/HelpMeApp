package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.TipsDAO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        setField();

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
                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                Call<TipsDAO> tipsDAOCall = apiService.editTips(getIntent().getStringExtra("id"),
                        title.getText().toString(),
                        description.getText().toString());
                System.out.println(getIntent().getStringExtra("id")+" "+
                        title.getText().toString()+" "+
                        description.getText().toString());
                tipsDAOCall.enqueue(new Callback<TipsDAO>() {
                    @Override
                    public void onResponse(Call<TipsDAO> call, Response<TipsDAO> response) {
                        Toast.makeText(EditTips.this, "Edit Success", Toast.LENGTH_SHORT).show();
                        startIntent();
                    }

                    @Override
                    public void onFailure(Call<TipsDAO> call, Throwable t) {
                        Toast.makeText(EditTips.this, "Edit Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setField(){
        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
    }

    public void startIntent(){
        Intent acc = new Intent(EditTips.this, MainActivity.class);
        acc.putExtra("from", "tips");
        startActivity(acc);
    }

}
