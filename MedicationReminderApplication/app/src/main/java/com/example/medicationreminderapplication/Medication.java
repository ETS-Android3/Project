package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.*;

public class Medication {
        String _name;
        String _GTIN;
        int _numLeft;
        Boolean _withFood;
        List<String> _takenAt;
        Map<String, Boolean> _prevTakenAt;
        void Medication(String name, String GTIN, int NumLeft, Boolean withFood, List<String> takenAt, Map<String, Boolean> prevTakenAt){
            _name = name;
            _GTIN = GTIN;
            _numLeft = NumLeft;
            _withFood = withFood;
            _takenAt = takenAt;
            _prevTakenAt = prevTakenAt;
        }
}
