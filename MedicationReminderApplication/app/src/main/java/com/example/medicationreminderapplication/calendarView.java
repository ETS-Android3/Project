package com.example.medicationreminderapplication;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class calendarView extends AppCompatActivity {
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    Context context;
    DataController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        dc = DataController.getInstance();
        context = this;
        DatePicker calendarView = findViewById(R.id.calendarView);
        calendarView.setMaxDate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        calendarView.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                final View medsOnSpecificDay = getLayoutInflater().inflate(R.layout.popup_medication_cal, null);
                RecyclerView recyclerView = medsOnSpecificDay.findViewById(R.id.recyclerMedicationDay);
                recyclerView.setAdapter(new MedicationOnDayRecyclerViewAdapter(context, dc.medicationsOn(year, monthOfYear, dayOfMonth)));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setView(medsOnSpecificDay);
                dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }
}