package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.UserDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private TextView btnLogin;
    private Button btnRegister;
    String TAG = "Register";

    private EditText etName,etPhone,etEmail,etUsername,etPassword;
    private TextView etBirth;
    private Spinner spinnerGender;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;// ...
// Initialize Firebase Auth

    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        btnLogin = (TextView) findViewById(R.id.tvLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etBirth = (TextView) findViewById(R.id.etBirth);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);

        datepicker(); // manggil kelas datepicker
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(Register.this, Login.class);
                startActivity(reg);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("masuk klik");

                if (cekDataUser()) {
                    mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Register();
                                sendEmail(user);
                                System.out.println("usernya " + user.getEmail());
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
            }
            }
        });
    }
    public void datepicker()
    {
        etBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Register.this,
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
                etBirth.setText(date);
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null)
        {
            Intent log = new Intent(Register.this, Login.class);
            startActivity(log);
        }
    }
    public void sendEmail(final FirebaseUser user)
    {
        final FirebaseUser firebase_user = user;
        firebase_user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //findViewById(R.id.verify_email_button).setEnabled(true);

                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, // text dibalik biar work :'v
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(Register.this,
                            "Verification email sent to " + firebase_user.getEmail()+"Please Verify First",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean cekDataUser(){
        //Mendapatkan dat yang diinputkan User
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        //Mengecek apakah email dan sandi kosong atau tidak
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Email and Password Empty", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            //Mengecek panjang karakter password baru yang akan didaftarkan
            if(password.length() < 6){
                Toast.makeText(this, "Too short, al least 6 character", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                return true;
                //createUserAccount();
            }
        }
    }

    /*private void createUserAccount(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Mengecek status keberhasilan saat medaftarkan email dan sandi baru
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(Register.this, "Terjadi Kesalahan, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    public void Register(){
       if (etName.getText().toString().isEmpty()){
            Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else{
        String selectedGender = spinnerGender.getSelectedItem().toString();
        System.out.println("masuk register detail "+etName.getText().toString()+" "+
                etPhone.getText().toString()+" "+
                etEmail.getText().toString()+" "+
                etUsername.getText().toString()+" "+
                etBirth.getText().toString()+" "+
                selectedGender);

            ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
            Call<UserDAO> userDAOCALL = apiService.addUser(etName.getText().toString(),
                    etPhone.getText().toString(),
                    etEmail.getText().toString(),
                    etUsername.getText().toString(),
                    etBirth.getText().toString(),
                    selectedGender);
        System.out.println("passing USERDAOCALL addUser");

            userDAOCALL.enqueue(new Callback<UserDAO>() {
                @Override
                public void onResponse(Call<UserDAO> call, Response<UserDAO> response) {
                    System.out.println("success");
                    Toast.makeText(Register.this, "Success creating account", Toast.LENGTH_SHORT).show();

                }
                // onrespone dan on Failure dibalik Toastnya
                @Override
                public void onFailure(Call<UserDAO> call, Throwable t) {
                    System.out.println("gagal");
                    Toast.makeText(Register.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
