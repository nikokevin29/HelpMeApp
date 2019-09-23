package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {
    private Context context;
    private List<TipsDAO> result;


    public TipsAdapter(Context context, List<TipsDAO> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_tips_adapter,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TipsDAO tips = result.get(position);
        holder.username.setText(tips.getUsername());
        holder.waktu.setText(tips.getTime());
        holder.judul.setText(tips.getTitle());
        holder.deskripsi.setText(tips.getDescription());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                Fragment fragment = new ViewTips();

                data.putString("username", tips.getUsername());
                data.putString("waktu", tips.getTime());
                data.putString("judul", tips.getTitle());
                data.putString("deskripsi", tips.getDescription());

                fragment.setArguments(data);
                loadFragment(fragment);
            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(tips);
                return false;
            }
        });
    }
    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
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
        private TextView username, waktu, judul, deskripsi;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            username = itemView.findViewById(R.id.tvNama);
            waktu =  itemView.findViewById(R.id.tvTanggaltips);
            judul = itemView.findViewById(R.id.tvJudul);
            deskripsi = itemView.findViewById(R.id.tvDeskripsitips);
            parent =  itemView.findViewById(R.id.ParentTips);
        }
        public void onClick(View view)
        {
            Toast.makeText(context, "You Tach Mi !!", Toast.LENGTH_SHORT).show();
        }
    }
    private void showDialog(final TipsDAO hasil){
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
                        deleteTips(hasil.getId());

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
    private void startIntent(TipsDAO hasil){
        Intent edit = new Intent(context, EditTips.class);
        edit.putExtra("id",hasil.getId());
        edit.putExtra("title", hasil.getTitle());
        edit.putExtra("description", hasil.getDescription());
        edit.putExtra("img", hasil.getImg());
        context.startActivity(edit);
    }
    private void deleteTips(String id){
        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<Void> tipsDAOCall = apiService.deleteTips(id);

        tipsDAOCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Success Deleting tips", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(context, "Fail Deleteing tips", Toast.LENGTH_SHORT).show();
                System.out.println("PISANG "+t.getMessage());
            }
        });

    }
}
