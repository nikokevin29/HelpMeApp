package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.banana.helpme.UserData.TipsDAO;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewTips extends Fragment {
    private TextView header, username, tanggal, judul, deskripsi;
    private ImageView image;
    private ImageButton nav_back;

    Bundle data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_tips, container, false);
        init(view);
        setField();

        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new TipsFragment());
            }
        });
        return view;
    }

    private void init(View view){
        header = (TextView) view.findViewById(R.id.tvHeaderTips);
        username = (TextView) view.findViewById(R.id.tvNamaTips);
        tanggal = (TextView) view.findViewById(R.id.tvTanggal);
        judul = (TextView) view.findViewById(R.id.tvJudulTips);
        deskripsi = (TextView) view.findViewById(R.id.tvDescription);
        image = (ImageView) view.findViewById(R.id.imgTips);

        nav_back = (ImageButton) view.findViewById(R.id.ic_back_view_tips);
    }

    private void setField(){
        data = this.getArguments();

        header.setText(data.getString("username"));
        username.setText(data.getString("username"));
        tanggal.setText(data.getString("waktu"));
        judul.setText(data.getString("judul"));
        deskripsi.setText(data.getString("deskripsi"));
        Glide.with(getActivity().getApplicationContext()).load(data.get("gambar")).into(image);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
