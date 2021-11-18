package com.example.medicationreminderapplication;
import java.time.LocalTime;
import java.util.*;

public class DataController {

    private LocalTime WakeUp;
    private LocalTime Breakfast;
    private LocalTime Lunch;
    private LocalTime Dinner;
    private LocalTime Bedtime;
    List<Medication> MedicationDict = new ArrayList<Medication>();
//Reads Data from the file and decrypts it
    void CollectData(){
        //Read line in file
        //Decrypt Line
        //Create medication obj
        //Add Medication to list
    }
//Checks for which medications are meant to be taken in the next 24 hours
    void NextMeds(){

    }
//Write Back To File
    void writeToFile(){

    }
//Adds new medications
    void newMed(String GTIN){
        
    }

}
