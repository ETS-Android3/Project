package com.example.medicationreminderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;


public class Scanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private  DataController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = DataController.getInstance();
        Context context = this;
        setContentView(R.layout.activity_scanner);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull com.google.zxing.Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Open Main page
                        Intent intent = new Intent(Scanner.this, mainPage.class);
                        String tempGTIN = result.getText(); //Get result from scanning
                        if (result.getBarcodeFormat().equals(BarcodeFormat.DATA_MATRIX)){ //If format is a data matrix, change format
                            try{
                                tempGTIN = tempGTIN.substring(3,17);
                                Log.e("Code", result.getText());
                                intent.putExtra("GTIN", tempGTIN);
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Toast.makeText(context, "Data Matrix doesn't contain enough data", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{ //If a barcode return the direct result
                            int length = tempGTIN.length();
                            intent.putExtra("GTIN", result.getText());
                        }
                        startActivity(intent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public void onNewMedNoScanClick(View view){
        Intent intent = new Intent(Scanner.this, mainPage.class);
        intent.putExtra("GTIN", "-1");
        startActivity(intent);
    }
}