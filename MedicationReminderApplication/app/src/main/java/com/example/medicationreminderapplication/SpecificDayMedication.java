package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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
