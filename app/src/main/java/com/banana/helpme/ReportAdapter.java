package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.UserData.ReportDAO;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    private Context context;
    private List<ReportDAO> result;

    public ReportAdapter(Context context, List<ReportDAO> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_report_adapter, parent, false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReportDAO report = result.get(position);
        holder.username.setText(report.getUsername());
        holder.waktu.setText(report.getDatetime());
        holder.alamat.setText(report.getAlamat());
        holder.deskripsi.setText(report.getDescription());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ViewReport());
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView username, waktu, kategori, alamat, deskripsi;
        private LinearLayout parent;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.tvNama);
            waktu = itemView.findViewById(R.id.tvTanggal);
            kategori = itemView.findViewById(R.id.tvKategori);
            alamat = itemView.findViewById(R.id.tvAlamat);
            deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            parent = itemView.findViewById(R.id.Parent);
        }
        public void onClick(View view) {
            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
        }
    }
}
