package com.banana.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banana.helpme.Api.ApiClient;
import com.banana.helpme.Api.ApiUserInterface;
import com.banana.helpme.UserData.ReportDAO;
import com.banana.helpme.UserData.TipsDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipsFragment extends Fragment {
    private List<TipsDAO> ListTips;
    private RecyclerView recyclerTips;
    private TipsAdapter tipsAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tips_fragment, container, false);

        recyclerTips = view.findViewById(R.id.recycler_view_tips);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListTips = new ArrayList<>();
        tipsAdapter = new TipsAdapter(getContext(),ListTips);
        mLayoutmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerTips.setLayoutManager(mLayoutmanager);
        recyclerTips.setItemAnimator(new DefaultItemAnimator());
        recyclerTips.setAdapter(tipsAdapter);
        setRecycleTips();
    }

    public void setRecycleTips(){
        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<List<TipsDAO>> tipsDAOCall = apiService.getAllTips();

        tipsDAOCall.enqueue(new Callback<List<TipsDAO>>() {
            @Override
            public void onResponse(Call<List<TipsDAO>> call, Response<List<TipsDAO>> response) {
                System.out.println(response.body().get(0).getTitle());
                ListTips.addAll(response.body());
                tipsAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<TipsDAO>> call, Throwable t) {

            }
        });

    }
}
