package com.example.medicationreminderapplication;

import android.hardware.biometrics.BiometricManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificDayMedication extends DayMedication {
    ArrayList<String> Days;
    public SpecificDayMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, ArrayList<String> Days, Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt, PrevTakenAt);
        this.Days = Days;
    }
    public SpecificDayMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, ArrayList<String> Days) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt);
        this.Days = Days;
    }
}
