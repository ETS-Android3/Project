package com.example.medicationreminderapplication;

import java.util.Map;

public class MonthlyMedication extends Medication {
    private int dayOfMonth;

    public MonthlyMedication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood, int dayOfMonth, Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, GTIN, NumLeft, type, WithFood, PrevTakenAt);
        this.dayOfMonth = dayOfMonth;
    }

    public MonthlyMedication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood, int dayOfMonth) {
        super(Name, strength, GTIN, NumLeft, type, WithFood);
        this.dayOfMonth = dayOfMonth;
    }
}
