package com.example.medicationreminderapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
        startActivity(intent);
    }

    void OpenNewMedsPopup(){
        dialogBuilder = new AlertDialog.Builder(context);
        final View addNewMedsView = getLayoutInflater().inflate(R.layout.popup, null);
        dialogBuilder.setView(addNewMedsView);
        dialog = dialogBuilder.create();
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
        TabLayout tabLayout = addNewMedsView.findViewById(R.id.tab_layout);
        ViewPager viewPager = addNewMedsView.findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        dialogBuilder.setView(addNewMedsView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
}