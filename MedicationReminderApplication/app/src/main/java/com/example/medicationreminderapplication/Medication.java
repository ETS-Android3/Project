package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.*;

public class Medication {
        String name;
        String GTIN;
        int numLeft;
        Boolean withFood;
        List<String> takenAt;
        Map<String, Boolean> prevTakenAt;
        void Medication(String Name, String GTIN, int NumLeft, Boolean WithFood, List<String> TakenAt, Map<String, Boolean> PrevTakenAt){
            name = Name;
            this.GTIN = GTIN;
            numLeft = NumLeft;
            withFood = WithFood;
            takenAt = TakenAt;
            prevTakenAt = PrevTakenAt;
        }

}
