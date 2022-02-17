package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.*;

public class Medication {
        String name;
        String Strength;
        String GTIN;
        String Type;
        int numLeft;
        Boolean withFood;
        List<String> takenAt;
        Map<String, Boolean> prevTakenAt;

        public Medication(String Name, String strength, String GTIN, int NumLeft, String type){
                name = Name;
                Strength = strength;
                this.GTIN = GTIN;
                Type = type;
                numLeft = NumLeft;
                withFood = true;
                takenAt = new ArrayList<String>();
                prevTakenAt = new HashMap<String, Boolean>();
        }

        public Medication(String Name, String strength, String GTIN, int NumLeft, String type, Boolean WithFood, List<String> TakenAt, Map<String, Boolean> PrevTakenAt){
            name = Name;
            Strength = strength;
            this.GTIN = GTIN;
            Type = type;
            numLeft = NumLeft;
            withFood = WithFood;
            takenAt = TakenAt;
            prevTakenAt = PrevTakenAt;
        }

}
