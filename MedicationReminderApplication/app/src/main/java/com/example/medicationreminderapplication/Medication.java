package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.*;

public abstract class Medication {
        String name;
        String Strength;
        String Type;
        int numLeft;
        Boolean withFood;
        Map<String, Boolean> prevTakenAt;

        protected Medication(String Name, String strength, int NumLeft, String type, Boolean WithFood, Map<String, Boolean> PrevTakenAt){
            name = Name;
            Strength = strength;
            Type = type;
            numLeft = NumLeft;
            withFood = WithFood;
            prevTakenAt = PrevTakenAt;
        }
        protected Medication(String Name, String strength, int NumLeft, String type, Boolean WithFood){
            name = Name;
            Strength = strength;
            Type = type;
            numLeft = NumLeft;
            withFood = WithFood;
            prevTakenAt = new HashMap<String, Boolean>();
        }

        public String toString(){
            return name.concat(" ").concat(Strength);
        }
}


