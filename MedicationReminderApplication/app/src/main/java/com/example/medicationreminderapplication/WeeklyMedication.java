package com.example.medicationreminderapplication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class WeeklyMedication extends DayMedication {
    ArrayList<String> Days;
    public WeeklyMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, ArrayList<String> Days, Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt, PrevTakenAt);
        this.Days = Days;
    }
    public WeeklyMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, ArrayList<String> Days) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt);
        this.Days = Days;
    }
}
