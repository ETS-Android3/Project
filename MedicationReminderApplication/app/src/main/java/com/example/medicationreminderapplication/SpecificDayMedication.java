package com.example.medicationreminderapplication;

import android.hardware.biometrics.BiometricManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificDayMedication extends Medication {
    ArrayList<ArrayList<LocalTime>> Times;
    public SpecificDayMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<ArrayList<LocalTime>> TakenAt, HashMap<String, Boolean> PrevTakenAt) {
        super(Name, strength, NumLeft, type, WithFood, PrevTakenAt);
        Times = TakenAt;
    }
    public SpecificDayMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<ArrayList<LocalTime>> TakenAt) {
        super(Name, strength, NumLeft, type, WithFood);
        Times = TakenAt;
    }
}
