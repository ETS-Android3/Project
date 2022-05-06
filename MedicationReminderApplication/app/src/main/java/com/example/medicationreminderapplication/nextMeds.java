package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.toolbox.Volley;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        medicationTaken.setAdapter(new TakenRecyclerViewAdapter(this));
        medicationTaken.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        dc.NextMeds();
        LocalDateTime nextDateTime = dc.getNextMedDateTime();
        Intent notifyIntent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 2, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = nextDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        super.onStop();
    }
}
