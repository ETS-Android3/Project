package com.example.medicationreminderapplication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class WeeklyMedication extends DayMedication {
    String Day;
    public WeeklyMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, String Day, Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt, PrevTakenAt);
        this.Day = Day;
    }
    public WeeklyMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, String Day) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt);
        this.Day = Day;
    }
}
