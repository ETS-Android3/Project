package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class mainPage extends AppCompatActivity {
    DataController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        dc = new DataController(getFilesDir());
    }

    public void onClick(View view){
        EditText text = (EditText)findViewById(R.id.editTxtGTIN);
        String GTIN = text.getText().toString();
        String name = dc.fromAPI(GTIN);
        TextView txt = (TextView) findViewById(R.id.txtTest);
        txt.setText(name);
    }
}