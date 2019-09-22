package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewReport extends Fragment {
    private TextView header, nama, tanggal, kategori, alamat, deskripsi;
    private ImageButton nav_back;

    Bundle data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_report, container, false);
        init(view);

        setField();

        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new HomeFragment());
            }
        });
        return view;
    }

    public void init(View view){
        header = (TextView) view.findViewById(R.id.tvHeaderRep);
        nama = (TextView) view.findViewById(R.id.tvNamaRep);
        tanggal = (TextView) view.findViewById(R.id.tvTanggalRep);
        kategori = (TextView) view.findViewById(R.id.tvKategori);
        alamat = (TextView) view.findViewById(R.id.tvLocationView);
        deskripsi = (TextView) view.findViewById(R.id.tvDescriptionRep);

        nav_back = (ImageButton) view.findViewById(R.id.ic_back_view_rep);


    }

    public void setField(){
        data = this.getArguments();

        header.setText(data.getString("username")+" - "+data.getString("kategori"));
        nama.setText(data.getString("username"));
        tanggal.setText(data.getString("waktu"));
        alamat.setText(data.getString("alamat"));
        kategori.setText(data.getString("kategori"));
        deskripsi.setText(data.getString("deskripsi"));
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
