package com.example.medicationreminderapplication;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import javax.net.ssl.HttpsURLConnection;

public class DataController {

    private static RequestQueue reqQueue;
    private LocalTime WakeUp;
    private LocalTime Breakfast;
    private LocalTime Lunch;
    private LocalTime Dinner;
    private LocalTime Bedtime;
    private static DataController instance = null;
    static ArrayList<Medication> MedicationList;

//Creates an instance of the Data controller and starts data reading
    public static DataController getInstance(File fileDirectory, RequestQueue requestQueue){
        if (instance == null){
            instance = new DataController(fileDirectory,requestQueue);
        }
        return instance;
    }

    public static DataController getInstance() {
        return instance;
    }

    private DataController(File fileDirectory, RequestQueue requestQueue){
        //Instantiate Medication list
        MedicationList = new ArrayList<Medication>();
        //Instantiate request queue
        reqQueue = requestQueue;
        reqQueue.start();
        //Collect Data
        CollectData(fileDirectory);
    }
//Reads Data from the file and decrypts it
    static void CollectData(File directory){
        //Open File
        File opened = new File(directory, "medInfo");
        //Read line in file

        //Decrypt Line/file
        //Close file
        //Create medication obj
        //Add Medication to list
    }
//Checks for which medications are meant to be taken next
    HashMap<Date, ArrayList<Medication>> NextMeds(){
        //Get current time
        Date curr = Calendar.getInstance().getTime();
        LocalDateTime current = LocalDateTime.now();
        //Get time in 24 hours
        //Check Daily Medications
        LocalDateTime nextDateTime = LocalDateTime.MAX;
        ArrayList<Medication> nextMeds = new ArrayList<>();
        for (Medication med: MedicationList
             ) {
            if (med instanceof EveryXDaysMedication){
                LocalDate previousDate = ((EveryXDaysMedication) med).startDate;
                ArrayList<LocalTime> previousTimes = ((EveryXDaysMedication) med).times;
                for (LocalTime time: previousTimes
                     ) {
                    LocalDateTime previousDateTime = previousDate.atTime(time);
                    while (previousDateTime.isBefore(current)){
                        previousDateTime = previousDateTime.plusDays(((EveryXDaysMedication) med).numberOfDays);
                    }
                    if (nextDateTime.isAfter(previousDateTime)){
                        nextDateTime = previousDateTime;
                        nextMeds.clear();
                        nextMeds.add(med);
                    }
                    else if (nextDateTime.isEqual(previousDateTime)){
                        nextMeds.add(med);
                    }
                }

            }
            else if (med instanceof SpecificDayMedication){
                ArrayList<ArrayList<LocalTime>> DayToTimes = ((SpecificDayMedication) med).Times;
                DayOfWeek now = current.getDayOfWeek();
                int Limit = now.getValue()-1;
                for (int i = 0; i < 7; i++) {
                    int currentInd = i+Limit;
                    if (currentInd > 6){
                        currentInd -= 7;
                    }
                    ArrayList<LocalTime> Times = DayToTimes.get(currentInd);
                    for (LocalTime time: Times
                         ) {
                        LocalDate tempDate = current.plusDays(currentInd).toLocalDate();
                        LocalDateTime tempDateTime = tempDate.atTime(time);
                        if (tempDateTime.isBefore(nextDateTime) && tempDateTime.isAfter(current)){
                            nextMeds.clear();
                            nextMeds.add(med);
                            nextDateTime = tempDateTime;
                        }
                        else if (tempDateTime.isEqual(current)){
                            nextMeds.add(med);
                        }
                    }
                }

                for (ArrayList<LocalTime> times: DayToTimes
                     ) {

                }
            }
            else if (med instanceof WeeklyMedication){

            }
            else if (med instanceof MonthlyMedication){

            }
        }
        return new HashMap<>();
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
    void newMed(Medication med){
        MedicationList.add(med);
    }
//Get medication information from API
    void fromAPI(String GTIN, final VolleyCallBack callBack){
        String url = "https://ampoule.herokuapp.com/gtin/"+GTIN; //URL for the API
        //Request for the API
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) { //When the API responds
                        try {
                            JSONObject jsonObj = response.getJSONObject("data"); //Retrieve data
                            callBack.onSuccess(jsonObj); //Send data
                        } catch (JSONException e) {
                            callBack.onFail(); //If no medication
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        reqQueue.add(jsonObjectRequest); // Add request to the request queue
    }
//Get All Current Medications
    ArrayList<Medication> medications(){
        return MedicationList;
    }
}
