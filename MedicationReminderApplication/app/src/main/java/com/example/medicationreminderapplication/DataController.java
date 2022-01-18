package com.example.medicationreminderapplication;
import android.content.Context;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DataController {

    private LocalTime WakeUp;
    private LocalTime Breakfast;
    private LocalTime Lunch;
    private LocalTime Dinner;
    private LocalTime Bedtime;
    List<Medication> MedicationList;

//Creates an instance of the Data controller and starts data reading
    public DataController(File fileDirectory){
        //Instantiate Medication list
        MedicationList = new ArrayList<Medication>();
        //Collect Data
        CollectData(fileDirectory);
    }
//Reads Data from the file and decrypts it
    void CollectData(File directory){
        //Open File
        File opened = new File(directory, "medInfo");
        //Read line in file

        //Decrypt Line/file
        //Close file
        //Create medication obj
        //Add Medication to list
    }
//Checks for which medications are meant to be taken in the next 24 hours
    Map<Date, Medication> NextMeds(){
        //Get current time
        Date curr = Calendar.getInstance().getTime();
        LocalDateTime current = LocalDateTime.now();
        //Get time in 24 hours
        LocalDateTime tomorrow = current + LocalDateTime.of;
        //Check Daily Medications
        HashMap<Date, ArrayList<Medication>> nextMeds = new HashMap<Date, ArrayList<Medication>>();
        for (int i = 0; i < MedicationList.size(); i++){
            Medication currentMed = MedicationList.get(i);
            for (int j = 0; j < currentMed.takenAt.size(); j++){
                switch (currentMed.takenAt.get(j)){
                    case "WakeUp":

                        break;
                    case "Breakfast":
                        break;
                    case "Lunch":
                        break;
                    case "Dinner":
                        break;
                    case "Bedtime":
                        break;
            }
        }

        return nextMeds;
    }
//Write Back To File
    void writeToFile(){
        //Open File / Create File
        //Encrypt times
        //Write times
        //encrypt medication list
        //write medication list
        //close file
    }
//Adds new medications
    void newMed(String GTIN){
        //Check if GTIN is already in the medication list
        //If already in medication list, add more quantity
        //If not in medication list add new medication

    }

}
