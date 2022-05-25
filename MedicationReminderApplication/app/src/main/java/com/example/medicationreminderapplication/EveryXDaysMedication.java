package com.example.medicationreminderapplication;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class EveryXDaysMedication extends DayMedication {
    int numberOfDays;
    LocalDate startDate;
    public EveryXDaysMedication(String Name, String strength,int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, int numOfDays ,LocalDate StartDate, Map<String, Boolean> PrevTakenAt) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt, PrevTakenAt);
        numberOfDays = numOfDays;
        startDate = StartDate;
    }
    public EveryXDaysMedication(String Name, String strength, int NumLeft, String type, Boolean WithFood, ArrayList<LocalTime> TakenAt, int numOfDays, LocalDate StartDate) {
        super(Name, strength, NumLeft, type, WithFood, TakenAt);
        numberOfDays = numOfDays;
        startDate = StartDate;
    }
}

