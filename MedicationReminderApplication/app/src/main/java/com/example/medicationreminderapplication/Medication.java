package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.*;

public abstract class Medication {
        String name;
        String Strength;
        String GTIN;
        String Type;
        int numLeft;
        Boolean withFood;
        Map<String, Boolean> prevTakenAt;

        protected Medication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood, Map<String, Boolean> PrevTakenAt){
            name = Name;
            Strength = strength;
            this.GTIN = GTIN;
            Type = type;
            numLeft = NumLeft;
            withFood = WithFood;
            prevTakenAt = PrevTakenAt;
        }
        protected Medication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood){
            name = Name;
            Strength = strength;
            this.GTIN = GTIN;
            Type = type;
            numLeft = NumLeft;
            withFood = WithFood;
            prevTakenAt = new HashMap<String, Boolean>();
        }
}


