package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class mainPage extends AppCompatActivity {
    DataController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        dc = new DataController(getFilesDir());
    }
}