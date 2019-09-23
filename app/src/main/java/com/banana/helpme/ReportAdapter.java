package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.ReportDAO;
import com.banana.helpme.UserData.TipsDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        final ReportDAO report = result.get(position);
        holder.username.setText(report.getUsername());
        holder.waktu.setText(report.getDatetime());
        holder.alamat.setText(report.getAddress());
        holder.deskripsi.setText(report.getDescription());
        holder.kategori.setText(report.getKategori());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                Fragment fragment = new ViewReport();

                data.putString("username", report.getUsername());
                data.putString("waktu", report.getDatetime());
                data.putString("alamat", report.getAddress());
                data.putString("deskripsi", report.getDescription());
                data.putString("kategori", report.getKategori());

                fragment.setArguments(data);
                loadFragment(fragment);
            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(report);
                return false;
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
        return result.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView username, waktu, kategori, alamat, deskripsi;
        private LinearLayout parent;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.tvNamareport);
            waktu = itemView.findViewById(R.id.tvTanggalreport);
            kategori = itemView.findViewById(R.id.tvKategorireport);
            alamat = itemView.findViewById(R.id.tvAlamat);
            deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            parent = itemView.findViewById(R.id.Parent);
        }
        public void onClick(View view) {
            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog(final ReportDAO hasil){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("What's next?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // update report
                        startIntent(hasil);
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete report
                        deleteReport(hasil.getId());
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void startIntent(ReportDAO hasil){
        Intent edit = new Intent(context, EditReport.class);
        edit.putExtra("id", hasil.getId());
        edit.putExtra("username", hasil.getUsername());
        edit.putExtra("waktu", hasil.getDatetime());
        edit.putExtra("alamat", hasil.getAddress());
        edit.putExtra("deskripsi", hasil.getDescription());
        edit.putExtra("kategori", hasil.getKategori());
        context.startActivity(edit);
    }

    private void deleteReport(String id){
        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<Void> reportDAOCall = apiService.deleteReport(id);

        reportDAOCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               //reverse close
                Toast.makeText(context, "Success Deleting report", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(context, "Fail Deleting report", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
