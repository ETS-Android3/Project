package com.example.medicationreminderapplication;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;



import javax.net.ssl.HttpsURLConnection;

public class DataController {

    private RequestQueue reqQueue;
    private LocalTime WakeUp;
    private LocalTime Breakfast;
    private LocalTime Lunch;
    private LocalTime Dinner;
    private LocalTime Bedtime;
    List<Medication> MedicationList;

//Creates an instance of the Data controller and starts data reading
    public DataController(File fileDirectory, RequestQueue requestQueue){
        //Instantiate Medication list
        MedicationList = new ArrayList<Medication>();
        //Instantiate request queue
        reqQueue = requestQueue;
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
            Medication a = fromAPI(GTIN);
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
        catch (Exception e){
            return new Medication();
        }
    }
//Get medication from API
    Medication fromAPI(String GTIN){
        String url = "https://ampoule.herokuapp.com/gtin/"+GTIN;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObj = response.getJSONObject("data");
                            String name = jsonObj.getString("name");
                            int strength = Integer.parseInt(jsonObj.getString("strength"));
                            String units = jsonObj.getString("units");
                            String type = jsonObj.getString("type");
                            int quantity = Integer.parseInt(jsonObj.getString("type"));
                            //Medication a = new Medication(name, GTIN, quantity, false, [""], [""]);
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        reqQueue.add(jsonObjectRequest);
        //return med[0];
        return new Medication();
    }
}
