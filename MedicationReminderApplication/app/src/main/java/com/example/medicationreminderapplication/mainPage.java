package com.example.medicationreminderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;

public class mainPage extends AppCompatActivity {
    public DataController dc;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        context = this;
        dc = DataController.getInstance(getFilesDir(), Volley.newRequestQueue(this));
        String gtin = getIntent().getStringExtra("GTIN");
        if (gtin!=null){
            if (gtin.equals("-1")){
                OpenNewMedsPopup();
            }
            else{
                dc.fromAPI(gtin, new VolleyCallBack() {
                    @Override
                    public void onSuccess(JSONObject information) {
                        try {
                            Toast.makeText(context, information.getString("name"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        OpenNewMedsPopup(information);
                    }

                    @Override
                    public void onFail() {
                        OpenNewMedsPopup();
                    }
                });
            }
        }
    }

    public void onNewMedsClick(View view){
        Intent intent = new Intent(mainPage.this, Scanner.class);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
    }

    public void onMedsListClick(View view){
        Intent intent = new Intent(mainPage.this, Medication_list.class);
        startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    void OpenNewMedsPopup(){
        dialogBuilder = new AlertDialog.Builder(context);
        final View addNewMedsView = getLayoutInflater().inflate(R.layout.popup, null);
        EditText nameText = (EditText) addNewMedsView.findViewById(R.id.editTextName);
        EditText dosageText = (EditText) addNewMedsView.findViewById(R.id.editTextDosage);
        EditText quantityText = (EditText) addNewMedsView.findViewById(R.id.editTextQuantity);
        Spinner typeDropDown = (Spinner) addNewMedsView.findViewById(R.id.spinnerType);
        ViewPager2 vp = (ViewPager2) addNewMedsView.findViewById(R.id.viewPager2);
        Button addButton = (Button) addNewMedsView.findViewById(R.id.btnAdd);
        vp.setAdapter(
                new Adaptery(this)
        );
        TabLayout tabLayout = (TabLayout) addNewMedsView.findViewById(R.id.tabs);
        new TabLayoutMediator(
                tabLayout,
                vp,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0){
                            tab.setText("By Day");
                        }
                        else if (position == 1){
                            tab.setText("By Week");
                        }
                        else{
                            tab.setText("By Month");
                        }
                    }
                }).attach();
        dialogBuilder.setView(addNewMedsView);
        dialog = dialogBuilder.create();
        addButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Medication newMed;
                Boolean allFull = Boolean.TRUE;
                String name = nameText.getText().toString();
                String dosage = dosageText.getText().toString();
                int quantity = Integer.parseInt(quantityText.getText().toString());
                String type = typeDropDown.getSelectedItem().toString();
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:{
                        Switch byDays = (Switch)vp.findViewById(R.id.switchByXDays);
                        if (byDays.isChecked()){
                            //TODO:: Get days
                            //TODO:: Get times
                            //TODO:: Add data to data controller
                        }
                        else{
                            //TODO::
                        }


                    }
                    case 1:{
                        Spinner daysSpinner = (Spinner) vp.findViewById(R.id.spinnerDayOfWeek);
                        String day = daysSpinner.getSelectedItem().toString();
                        RecyclerView timeRV = (RecyclerView)  vp.findViewById(R.id.recyclerByWeekTimes);
                        RecyclerViewAdapter timeRVA = (RecyclerViewAdapter)timeRV.getAdapter();
                        ArrayList<LocalTime> times = timeRVA.mTimes;
                        ArrayList<String> days = new ArrayList<>();
                        days.add(day);
                        newMed = new SpecificDayMedication(name, dosage, quantity, type, Boolean.TRUE, times, days);
                        dc.newMed(newMed);
                    }
                    case 2:{
                        EditText monthText = (EditText) vp.findViewById(R.id.editTextDayOfMonth);
                        int month = Integer.parseInt(monthText.getText().toString());
                        newMed = new MonthlyMedication(name, dosage, quantity, type, Boolean.TRUE, month);
                        dc.newMed(newMed);
                    }

                }
                if (allFull) { dialog.dismiss();}
                return false;
            }
        });
        dialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    void OpenNewMedsPopup(JSONObject information){
        final View addNewMedsView = getLayoutInflater().inflate(R.layout.popup, null);
        dialogBuilder = new AlertDialog.Builder(context);
        EditText nameText = (EditText) addNewMedsView.findViewById(R.id.editTextName);
        EditText dosageText = (EditText) addNewMedsView.findViewById(R.id.editTextDosage);
        EditText quantityText = (EditText) addNewMedsView.findViewById(R.id.editTextQuantity);
        Spinner typeDropDown = (Spinner) addNewMedsView.findViewById(R.id.spinnerType);
        ViewPager2 vp = (ViewPager2) addNewMedsView.findViewById(R.id.viewPager2);
        Button addButton = (Button) addNewMedsView.findViewById(R.id.btnAdd);
        if (information.toString() != "null"){
            try{
                nameText.setText(information.getString("name"));
                nameText.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                dosageText.setText(information.getString("strength").concat(information.getString("units")));
                dosageText.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                quantityText.setText(information.getString("quantity"));
                quantityText.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        vp.setAdapter(
                new Adaptery(this)
        );
        TabLayout tabLayout = (TabLayout) addNewMedsView.findViewById(R.id.tabs);
        new TabLayoutMediator(
                tabLayout,
                vp,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0){
                            tab.setText("By Day");
                        }
                        else if (position == 1){
                            tab.setText("By Week");
                        }
                        else{
                            tab.setText("By Month");
                        }
                    }
                }).attach();

        dialogBuilder.setView(addNewMedsView);
        dialog = dialogBuilder.create();
        addButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Medication newMed;
                Boolean allFull = Boolean.TRUE;
                String name = nameText.getText().toString();
                String dosage = dosageText.getText().toString();
                int quantity = Integer.parseInt(quantityText.getText().toString());
                String type = typeDropDown.getSelectedItem().toString();
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:{
                        Switch byDays = (Switch)vp.findViewById(R.id.switchByXDays);
                        if (byDays.isChecked()){
                            //TODO:: Get days
                            //TODO:: Get times
                            //TODO:: Add data to data controller
                        }
                        else{
                            //TODO::
                        }


                    }
                    case 1:{
                        Spinner daysSpinner = (Spinner) vp.findViewById(R.id.spinnerDayOfWeek);
                        String day = daysSpinner.getSelectedItem().toString();
                        RecyclerView timeRV = (RecyclerView)  vp.findViewById(R.id.recyclerByWeekTimes);
                        RecyclerViewAdapter timeRVA = (RecyclerViewAdapter)timeRV.getAdapter();
                        ArrayList<LocalTime> times = timeRVA.mTimes;
                        Toast.makeText(context, String.join(" ", name, dosage, String.valueOf(quantity), type, day, times.toString()), Toast.LENGTH_SHORT).show();
                        //TODO:: Convert data into a medication
                        ArrayList<String> days = new ArrayList<>();
                        days.add(day);
                        newMed = new SpecificDayMedication(name, dosage, quantity, type, Boolean.TRUE, times, days);
                        dc.newMed(newMed);
                    }
                    case 2:{
                        //TODO:: Get date of month
                        //TODO:: Get times of day
                        //TODO:: Add data to data controller
                    }

                }
                if (allFull) { dialog.dismiss();}
                return false;
            }
        });
        dialog.show();
    }


    class Adaptery extends FragmentStateAdapter {

        public Adaptery(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public Adaptery(@NonNull Fragment fragment) {
            super(fragment);
        }

        public Adaptery(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0){
                return new ByDay();
            }
            else if (position == 1){
                return new ByWeek();
            }
            else{
                return new ByMonth();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}