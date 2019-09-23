package com.banana.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.UserDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccount extends AppCompatActivity {

    public EditText nama, phone, email, username, password;
    public TextView birth;
    private Spinner spinnerGender;

    private Button Confirm;
    private ImageButton NavBack;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    UserDAO userDAO;
    String TAG = "EditAccount";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        NavBack = (ImageButton) findViewById(R.id.ic_back);
        Confirm = (Button) findViewById(R.id.btnConfirm);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        nama =  findViewById(R.id.etName);
        phone = findViewById(R.id.etPhone);
        email = findViewById(R.id.etEmail);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        birth = findViewById(R.id.etBirth);
        spinnerGender = findViewById(R.id.spinnerGender);

        email.setEnabled(false);
        setField();

        NavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nav = new Intent(EditAccount.this, MainActivity.class);
                nav.putExtra("from", "account");
                startActivity(nav);
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();
                Intent con = new Intent(EditAccount.this, MainActivity.class);
                con.putExtra("from", "account");
                startActivity(con);
            }
        });

        datepicker();
    }

    private void setField(){
        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        System.out.println(user.getEmail());
        Call<List<UserDAO>> userDAOCall = apiService.getAllUser();
        userDAOCall.enqueue(new Callback<List<UserDAO>>() {
            @Override
            public void onResponse(Call<List<UserDAO>> call, Response<List<UserDAO>> response) {
                for (int i=0; i<response.body().size(); i++){
                    if(response.body().get(i).getEmail().equals(user.getEmail())){
                        System.out.println(response.body().get(i).getEmail());
                        userDAO = response.body().get(i);
                        System.out.println(userDAO.getName()+userDAO.getGender()+userDAO.getBirth()+userDAO.getUsername()+userDAO.getPhone()+userDAO.getEmail());
                        System.out.println(response.body().get(i).getName()+" "+response.body().get(i).getPhone()+" "+response.body().get(i).getEmail()+" "+
                                response.body().get(i).getUsername()+" "+response.body().get(i).getBirth()+" "+response.body().get(i).getGender());

                        nama.setText(userDAO.getName());
                        phone.setText(userDAO.getPhone());
                        email.setText(userDAO.getEmail());
                        username.setText(userDAO.getUsername());
                        birth.setText(userDAO.getBirth());
                        spinnerGender.setSelection(getIndexByString(spinnerGender, userDAO.getGender()));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserDAO>> call, Throwable t) {
                System.out.println("gagal");
            }
        });
    }
    public void datepicker()
    {
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditAccount.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd-mm-yyyy: " + day + "-" + month + "-" + year);

                String date = day + " - " +  month + " - " + year;
                birth.setText(date);
            }
        };
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

    private void updateProfile(){
        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        System.out.println(user.getEmail());
        Call<UserDAO> userDAOCall = apiService.editUser(email.getText().toString(), nama.getText().toString(),
                phone.getText().toString(), username.getText().toString(), birth.getText().toString(), spinnerGender.getSelectedItem().toString());
        userDAOCall.enqueue(new Callback<UserDAO>() {
            @Override
            public void onResponse(Call<UserDAO> call, Response<UserDAO> response) {
                Toast.makeText(EditAccount.this, "Success", Toast.LENGTH_SHORT).show();
                startIntent();

            }

            @Override
            public void onFailure(Call<UserDAO> call, Throwable t) {
                Toast.makeText(EditAccount.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startIntent(){
        Intent acc = new Intent(EditAccount.this, MainActivity.class);
        acc.putExtra("from", "account");
        startActivity(acc);
    }
}
