package com.example.medicationreminderapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

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

    @SuppressLint("ClickableViewAccessibility")
    void OpenNewMedsPopup(){
        dialogBuilder = new AlertDialog.Builder(context);
        final View addNewMedsView = getLayoutInflater().inflate(R.layout.popup, null);
        EditText name = (EditText) addNewMedsView.findViewById(R.id.editTextName);
        EditText dosage = (EditText) addNewMedsView.findViewById(R.id.editTextDosage);
        EditText quantity = (EditText) addNewMedsView.findViewById(R.id.editTextQuantity);
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

                dialog.dismiss();
                return false;
            }
        });
        dialog.show();
    }

    void OpenNewMedsPopup(JSONObject information){
        dialogBuilder = new AlertDialog.Builder(context);
        final View addNewMedsView = getLayoutInflater().inflate(R.layout.popup, null);
        EditText name = (EditText) addNewMedsView.findViewById(R.id.editTextName);
        EditText dosage = (EditText) addNewMedsView.findViewById(R.id.editTextDosage);
        EditText quantity = (EditText) addNewMedsView.findViewById(R.id.editTextQuantity);
        if (information.toString() != "null"){
            try{
                name.setText(information.getString("name"));
                name.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                dosage.setText(information.getString("strength").concat(information.getString("units")));
                dosage.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                quantity.setText(information.getString("quantity"));
                quantity.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        dialogBuilder.setView(addNewMedsView);
        dialog = dialogBuilder.create();
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