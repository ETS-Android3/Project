package com.example.medicationreminderapplication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EveryXDaysMedication extends DayMedication {
    int numberOfDays;
    public EveryXDaysMedication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, int numOfDays , Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, GTIN, NumLeft, type, WithFood, TakenAt, PrevTakenAt);
        numberOfDays = numOfDays;
    }
    public EveryXDaysMedication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, int numOfDays) {
        super(Name, strength, GTIN, NumLeft, type, WithFood, TakenAt);
        numberOfDays = numOfDays;
    }
}

