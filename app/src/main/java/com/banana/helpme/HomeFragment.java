package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.ReportDAO;
import com.banana.helpme.UserData.UserDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private List<ReportDAO> ListReport;
    private RecyclerView recyclerReport;
    private ReportAdapter adapterReport;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        recyclerReport = view.findViewById(R.id.recycler_view_report);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListReport = new ArrayList<>();
        adapterReport = new ReportAdapter(getContext(), ListReport);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerReport.setLayoutManager(mLayoutManager);
        recyclerReport.setItemAnimator(new DefaultItemAnimator());
        recyclerReport.setAdapter(adapterReport);
        setRecycleView();
    }

    public void setRecycleView(){
        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<List<ReportDAO>> reportDAOCall = apiService.getAllReport();

        reportDAOCall.enqueue(new Callback<List<ReportDAO>>() {
            @Override
            public void onResponse(Call<List<ReportDAO>> call, Response<List<ReportDAO>> response) {
                ListReport.addAll(response.body());
                System.out.println(ListReport.get(0).getAddress());
                adapterReport.notifyDataSetChanged();
//                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ReportDAO>> call, Throwable t) {
                //Toast.makeText(getContext(), "Failed to load report", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
