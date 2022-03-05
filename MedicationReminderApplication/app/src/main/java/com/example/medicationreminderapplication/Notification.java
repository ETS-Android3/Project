package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Notification extends AppCompatActivity {
    DataController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }
}