package com.example.medicationreminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int LOCK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyguardManager kgm = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        Intent screenLockIntent = kgm.createConfirmDeviceCredentialIntent("", "");

        startActivityForResult(screenLockIntent, LOCK_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(LOCK_REQUEST_CODE == requestCode){
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(MainActivity.this, mainPage.class);
                startActivity(intent);
            } else {
                //Authentication failed
            }
        }
    }
}