package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        setField();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
                Call<String> reportDAOCall = apiService.editReport(getIntent().getStringExtra("id"),
                        category.getSelectedItem().toString(), "example image",
                        description.getText().toString());

                System.out.println(getIntent().getStringExtra("id")+" "+
                        category.getSelectedItem().toString()+" example image "+" "+
                        description.getText().toString());
                reportDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(EditReport.this, "Edit Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
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


}
