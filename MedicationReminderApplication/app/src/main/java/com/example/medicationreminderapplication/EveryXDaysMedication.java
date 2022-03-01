package com.example.medicationreminderapplication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EveryXDaysMedication extends DayMedication {
    int numberOfDays;
    public EveryXDaysMedication(String Name, String strength,int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, int numOfDays , Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt, PrevTakenAt);
        numberOfDays = numOfDays;
    }
    public EveryXDaysMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, int numOfDays) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt);
        numberOfDays = numOfDays;
    }
}

