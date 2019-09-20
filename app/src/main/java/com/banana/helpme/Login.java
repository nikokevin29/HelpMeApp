package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText etEmail, etPassword;
    private TextView btnRegister;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading..");
        progressDialog.setMessage("Verify data");
        progressDialog.setCancelable(false);

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

        user = mAuth.getCurrentUser();

        if (user!=null)
        {
            if(user.isEmailVerified())
            {
                loggedIn = isLoggedIn();
                if (loggedIn) {
                    goToMain(); //go to mainactivity

                }
            }/*else{
                sendEmail(user);
            }*/
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //else{
                progressDialog.show();
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

                    user = mAuth.getCurrentUser();
                    //System.out.println(user.getEmail()+" "+user.isEmailVerified());
                    if(user.isEmailVerified())
                    {
                        goToMain();

                        //proses data profil disini
                    }else{
                        sendEmail(user);

                    }
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

    public void sendEmail(final FirebaseUser user)
    {
        final FirebaseUser firebase_user = user;
        firebase_user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //findViewById(R.id.verify_email_button).setEnabled(true);

                if (task.isSuccessful()) {
                    showMessageBox("Akun anda belum terverifikasi. Email verifikasi telah terkirim ke "+firebase_user.getEmail()+
                            ". Login ulang setelah melakukan verifikasi akun.");
                    //Toast.makeText(Login.this,
                    //        "Verification email sent to " + firebase_user.getEmail(),
                    //        Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(Login.this,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
