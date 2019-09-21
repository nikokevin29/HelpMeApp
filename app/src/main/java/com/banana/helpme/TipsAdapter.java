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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.helpme.UserData.TipsDAO;

import java.util.List;

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
        View v = LayoutInflater.from(context).inflate(R.layout.activity_view_tips,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TipsDAO tips = result.get(position);
        holder.username.setText(tips.getUsername());
        holder.waktu.setText(tips.getTime());
        holder.judul.setText(tips.getTitle());
        holder.deskripsi.setText(tips.getDescription());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ViewTips());
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
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView username, waktu, judul, deskripsi;
        private LinearLayout parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            username = itemView.findViewById(R.id.tvNama);
            waktu =  itemView.findViewById(R.id.tvTanggal);
            judul = itemView.findViewById(R.id.tvJudul);
            deskripsi = itemView.findViewById(R.id.tvDescription);
            parent =  itemView.findViewById(R.id.parent);
        }
        public void onClick(View view)
        {
            Toast.makeText(context, "You Tach Mi !!", Toast.LENGTH_SHORT).show();
        }
    }
}
