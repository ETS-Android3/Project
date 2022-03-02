package com.example.medicationreminderapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.ArrayList;


public class ByXDays extends Fragment {

    public ByXDays() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_by_x_days, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerByWeekTimes);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), new ArrayList<LocalTime>());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FloatingActionButton addButton = root.findViewById(R.id.btnAddTime2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.mTimes.add(LocalTime.MIN);
                adapter.notifyDataSetChanged();
                Log.d("String", adapter.mTimes.toString());
            }
        });
        return root;
    }


}