package com.example.medicationreminderapplication;
import android.content.Context;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

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
//Checks for which medications are meant to be taken in the next 'x' days
    HashMap<Date, ArrayList<Medication>> NextMeds(int days){
        //Get current time
        Date curr = Calendar.getInstance().getTime();
        LocalDateTime current = LocalDateTime.now();
        //Get time in 24 hours
        //Check Daily Medications
        HashMap<Date, ArrayList<Medication>> nextMeds = new HashMap<Date, ArrayList<Medication>>();
        for (int i = 0; i < MedicationList.size(); i++){
            Medication currentMed = MedicationList.get(i);
            for (int j = 0; j < currentMed.takenAt.size(); j++) {
                switch (currentMed.takenAt.get(j)) {
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
        }
        return nextMeds;
    }
//Write Back To File
    public void writeToFile(){
        //Open File / Create File
        //Encrypt times
        //Write times
        //encrypt medication list
        //write medication list
        //close file
    }
//Adds new medications
    Medication newMed(String GTIN){
        try{
            //Find information from API
            URL apiRequest = new URL("https://ampoule.herokuapp.com/gtin/"+GTIN);
            HttpsURLConnection conn = (HttpsURLConnection) apiRequest.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode != 200){
                throw new RuntimeException();
            }
            else{
            //Check if GTIN is already in the medication list
            for (int index = 0; index < MedicationList.size(); index++){
                //If already in medication list, add more quantity
                if (MedicationList.get(index).GTIN == GTIN){
                    MedicationList.get(index).numLeft += 5;
                }
            }
            //If not in medication list add new medication
            Medication newMED = new Medication();
            MedicationList.add(newMED);
            return newMED;
            }
        }
        catch (Exception e){
            return new Medication();
        }
    }
//Get medication from API
    String fromAPI(String GTIN){
        String a = "AAA";
        try{
            //Find information from API
            a = "A";
            URL apiRequest = new URL("https://ampoule.herokuapp.com/gtin/"+GTIN);
            a = "BBB";
            HttpsURLConnection conn = (HttpsURLConnection) apiRequest.openConnection();
            a = "CCC";
            conn.setRequestMethod("GET");
            a = "DDD";
            conn.connect();
            a="EEE";

            int responsecode = conn.getResponseCode();
            if (responsecode != 200){
                return "responscodeError";
            }
            else{
                //String inline = "";
                //Scanner scanner = new Scanner(apiRequest.openStream());

                //while (scanner.hasNext()){
                //    inline += scanner.nextLine();
                //}
                //scanner.close();

                //JSONObject data_obj = new JSONObject(inline);
                return "AAA";
            }
        }
        catch (Exception e){
            return e.toString();
        }
    }

}
