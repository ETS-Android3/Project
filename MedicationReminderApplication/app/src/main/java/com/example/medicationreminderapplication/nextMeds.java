package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;

public class nextMeds extends AppCompatActivity {

    public DataController dc;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_meds);
        context = this;
        dc = DataController.getInstance(context, Volley.newRequestQueue(context));
    }
}