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

public class AddFragment extends Fragment {

    private ImageButton report, tips;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_fragment, container, false);

        report = (ImageButton) view.findViewById(R.id.btnReport);
        tips = (ImageButton) view.findViewById(R.id.btnTips);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRep = new Intent(getActivity(),AddReport.class);
                startActivity(addRep);
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTips = new Intent(getActivity(),AddTips.class);
                startActivity(addTips);
            }
        });

        return view;
    }


}
