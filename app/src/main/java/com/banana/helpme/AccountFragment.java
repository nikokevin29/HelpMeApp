package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.UserDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.*;

public class AccountFragment extends Fragment {
    private UserDAO userModel = new UserDAO();
    private TextView tvNama, tvUsername, tvEmail, tvPhone, tvBirthday, tvGender;
    //private ImageButton mlogout;
    private RelativeLayout btnLogout, btnEdit;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account_fragment, container, false);
        tvNama = (TextView) view.findViewById(R.id.tvFullName);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        tvBirthday = (TextView) view.findViewById(R.id.tvDate);
        tvGender = (TextView) view.findViewById(R.id.tvGender);

        btnLogout = (RelativeLayout) view.findViewById(R.id.btnLogout);
        btnEdit = (RelativeLayout) view.findViewById(R.id.btnEditProfile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        tvNama.setText(userModel.getNama());
        tvUsername.setText(userModel.getUsername());
        showInfo();
        logout(); //fungsi button logout
        return view;

    }
    public void showInfo()
    {

    }

    public void logout()
    {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(),Login.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }
}
