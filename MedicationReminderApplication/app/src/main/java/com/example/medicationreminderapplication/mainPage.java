package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class mainPage extends AppCompatActivity {
    public DataController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        dc = new DataController(getFilesDir(), Volley.newRequestQueue(this));
    }

    public void onClick(View view){

    }

    public void onNewMedsClick(View view){
        Intent intent = new Intent(mainPage.this, Scanner.class);
        startActivity(intent);
    }
}