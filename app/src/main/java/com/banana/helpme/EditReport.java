package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.ReportDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditReport extends AppCompatActivity {

    private Button confirm;
    private ImageButton nav_back;
    Spinner  category;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);
        confirm = (Button) findViewById(R.id.btnConfirmEditReport);
        category = (Spinner) findViewById(R.id.spinnerCategory);
        description = (EditText) findViewById(R.id.etDescription);
        nav_back = (ImageButton) findViewById(R.id.ic_nav_back_edit_report);

        setField();

        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(EditReport.this, MainActivity.class);
                startActivity(main);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                Call<ReportDAO> reportDAOCall = apiService.editReport(getIntent().getStringExtra("id"),
                        category.getSelectedItem().toString(), "example image",
                        description.getText().toString());
                reportDAOCall.enqueue(new Callback<ReportDAO>() {
                    @Override
                    public void onResponse(Call<ReportDAO> call, Response<ReportDAO> response) {
                        Toast.makeText(EditReport.this, "Edit Success", Toast.LENGTH_SHORT).show();
                        startIntent();
                    }

                    @Override
                    public void onFailure(Call<ReportDAO> call, Throwable t) {
                        Toast.makeText(EditReport.this, "Edit Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setField(){
        category.setSelection(getIndexByString(category, getIntent().getStringExtra("kategori")));
        description.setText(getIntent().getStringExtra("deskripsi"));
    }

    private int getIndexByString(Spinner spinner, String string) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void startIntent(){
        Intent acc = new Intent(EditReport.this, MainActivity.class);
        startActivity(acc);
    }


}
