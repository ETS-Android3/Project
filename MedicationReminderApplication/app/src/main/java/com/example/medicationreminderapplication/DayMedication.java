package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public abstract class DayMedication extends Medication {
    ArrayList<LocalTime> times;
    public DayMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, Map<String, Boolean> PrevTakenAt){
        super(Name,strength,NumLeft, type, WithFood, PrevTakenAt);
        times = TakenAt;
    }

    public DayMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt){
        super(Name,strength, NumLeft, type, WithFood);
        times = TakenAt;
    }
}