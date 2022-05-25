package com.example.medicationreminderapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class Medication_list extends AppCompatActivity {

    DataController dc;
    MedicationRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);
        dc = DataController.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recyclerMedicationList);
        adapter = new MedicationRecyclerViewAdapter(this, dc.medications());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}