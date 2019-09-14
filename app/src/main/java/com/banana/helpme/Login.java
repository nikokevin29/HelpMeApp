package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText etEmail, etPassword;
    private TextView btnRegister;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();
        btnRegister = (TextView) findViewById(R.id.textRegister); //init button register
        btnLogin = (Button) findViewById(R.id.btnLogin); // init button login

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(Login.this, Register.class);
                startActivity(reg);
            }
        });
        loggedIn = isLoggedIn();
        if (loggedIn) {
            goToMain(); //go to mainactivity
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //else{
                    String email = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    login(email, password);
                //}

            }
        });
    }


    private void goToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void login(final String email, final String password)
    {
        if (TextUtils.isEmpty(email)) {
            Snackbar.make(findViewById(android.R.id.content), "Masukan email yang valid", Snackbar.LENGTH_LONG)
                    .show();
        } else if (TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(android.R.id.content), "Masukan password yang valid", Snackbar.LENGTH_LONG)
                    .show();
        } else {
            //  do login
            mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //  login sucess
                        //  go to mainactivity
                        goToMain();
                    } else {
                        //  login failed
                        showMessageBox("Login failed. Your username and password is not matched");
                    }
                }
            });
        }
    }

    private void showMessageBox(String message)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Login");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.show();
    }
    public boolean isLoggedIn() {
        if (mAuth.getCurrentUser() != null) {
            //  user logged in
            return true;
        } else {
            return false;
        }
    }
}
