package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class nextMeds extends AppCompatActivity {

    public DataController dc;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_meds);
        context = this;
        dc = DataController.getInstance(context, Volley.newRequestQueue(context));
        RecyclerView medicationTaken = this.findViewById(R.id.recyclerTakenMedication);
        medicationTaken.setAdapter(new TimesRecyclerViewAdapter(this, new ArrayList<>(), days));
        medicationTaken.setLayoutManager(new LinearLayoutManager(this));
    }
}
