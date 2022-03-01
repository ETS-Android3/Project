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
//Checks for which medications are meant to be taken in the next 'x' days
/*
    HashMap<Date, ArrayList<Medication>> NextMeds(int days){
        //Get current time
        Date curr = Calendar.getInstance().getTime();
        LocalDateTime current = LocalDateTime.now();
        //Get time in 24 hours
        //Check Daily Medications

        HashMap<Date, ArrayList<Medication>> nextMeds = new HashMap<Date, ArrayList<Medication>>();
        for (int i = 0; i < MedicationList.size(); i++){
            Medication currentMed = MedicationList.get(i);
            for (int j = 0; j < currentMed..size(); j++) {
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
    }*/
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
    List<Medication> medications(){
        return MedicationList;
    }
}
