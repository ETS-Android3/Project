package com.example.medicationreminderapplication;
import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKeys;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
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
    private LocalDateTime nextMedDateTime = null;
    private ArrayList<Medication> nextMedList = null;
    Context context;

//Creates an instance of the Data controller and starts data reading
    public static DataController getInstance(Context context, RequestQueue requestQueue){
        if (instance == null){
            instance = new DataController(context,requestQueue);
        }
        return instance;
    }

    public static DataController getInstance() {
        return instance;
    }

    private DataController(Context context, RequestQueue requestQueue){
        this.context = context;
        //Instantiate Medication list
        MedicationList = new ArrayList<Medication>();
        //Instantiate request queue
        reqQueue = requestQueue;
        reqQueue.start();
        //Collect Data
        CollectData();
    }
//Reads Data from the file and decrypts it
    void CollectData(){
// Although you can define your own key generation parameter specification, it's
// recommended that you use the value specified here.
        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        String mainKeyAlias = null;
        try {
            mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            String fileToRead = "MedicationInfo.txt";
            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    new File(context.getFilesDir(), fileToRead),
                    context,
                    mainKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            InputStream inputStream = encryptedFile.openFileInput();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int nextByte = inputStream.read();
            while (nextByte != -1) {
                byteArrayOutputStream.write(nextByte);
                nextByte = inputStream.read();
            }

            byte[] plaintext = byteArrayOutputStream.toByteArray();
            Log.e("Decrypted", plaintext.toString());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
//Checks for which medications are meant to be taken next
    void NextMeds(){
        //Get current time
        LocalDateTime current = LocalDateTime.now();
        //Check Daily Medications
        LocalDate nextDate = LocalDate.MAX;
        ArrayList<Medication> nextMonthlyMeds = new ArrayList<>();
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

                    if (previousDateTime.toLocalDate().isBefore(nextDate)){
                        nextMeds.clear();
                        nextMonthlyMeds.clear();
                        nextMeds.add(med);
                        nextDateTime = previousDateTime;
                        nextDate = previousDateTime.toLocalDate();
                    }
                    else if (nextDateTime.isAfter(previousDateTime)){
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
                        if (tempDateTime.toLocalDate().isBefore(nextDate) && tempDateTime.isAfter(current)){
                            nextMeds.clear();
                            nextMonthlyMeds.clear();
                            nextMeds.add(med);
                            nextDateTime = tempDateTime;
                            nextDate = tempDateTime.toLocalDate();
                        }
                        else if (tempDateTime.isBefore(nextDateTime) && tempDateTime.isAfter(current)){
                            nextMeds.clear();
                            nextMeds.add(med);
                            nextDateTime = tempDateTime;
                        }
                        else if (tempDateTime.isEqual(nextDateTime)){
                            nextMeds.add(med);
                        }
                    }
                }
            }
            else if (med instanceof WeeklyMedication){
                String day = ((WeeklyMedication) med).Day;
                ArrayList<LocalTime> times = ((WeeklyMedication) med).times;
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase(Locale.ROOT));

                DayOfWeek now = current.getDayOfWeek();

                int daysUntil;
                if (dayOfWeek.getValue()> now.getValue()){
                    daysUntil = dayOfWeek.getValue()- now.getValue();
                }
                else{
                    daysUntil = 7-(now.getValue()-dayOfWeek.getValue());
                }

                LocalDate tempDate = current.toLocalDate();
                for (LocalTime time: times
                     ) {
                    LocalDateTime tempDateTime = tempDate.atTime(time);

                    if (tempDateTime.toLocalDate().isBefore(nextDate)){
                        nextMeds.clear();
                        nextMonthlyMeds.clear();
                        nextMeds.add(med);
                        nextDateTime = tempDateTime;
                        nextDate = tempDateTime.toLocalDate();
                    }
                    else if (tempDateTime.isBefore(nextDateTime)){
                        nextMeds.clear();
                        nextMeds.add(med);
                        nextDateTime = tempDateTime;
                    }
                    else if (tempDateTime.isEqual(nextDateTime)){
                        nextMeds.add(med);
                    }
                }
            }
            else if (med instanceof MonthlyMedication){
                int dayOfMonth = ((MonthlyMedication) med).dayOfMonth;
                LocalDate currentDate = current.toLocalDate();
                int lastDayOfMonth = currentDate.lengthOfMonth();
                if (dayOfMonth > lastDayOfMonth){
                    dayOfMonth = lastDayOfMonth;
                }
                if (currentDate.getDayOfMonth() < dayOfMonth){
                    LocalDate tempDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), dayOfMonth);
                    if (tempDate.isBefore(nextDate)){
                        nextMonthlyMeds.clear();
                        nextMonthlyMeds.add(med);
                        nextDate = tempDate;
                        nextDateTime = tempDate.atTime(LocalTime.NOON);
                    }

                }
            }
        }
        nextMeds.addAll(nextMonthlyMeds);
        nextMedList=nextMeds;
        nextMedDateTime = nextDateTime;
        writeToFile();
    }

    public ArrayList<Medication> getNextMedList() {
        return nextMedList;
    }

    public LocalDateTime getNextMedDateTime() {
        return nextMedDateTime;
    }

    //Write Back To File
    public void writeToFile(){
        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try {
            String mainKeyAlis = MasterKeys.getOrCreate(keyGenParameterSpec);
            String fileToWrite = "MedicationInfo.txt";
            Log.e("DIRECTORY", context.getFilesDir().toString());
            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    new File(context.getFilesDir(),fileToWrite),
                    context,
                    mainKeyAlis,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();
            String fileString = "";
            for (Medication med: MedicationList
                 ) {
                if (med instanceof EveryXDaysMedication){
                    fileString = fileString.concat("EveryXDay\n");
                    fileString = fileString.concat(med.name);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Strength);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(String.valueOf(med.numLeft));
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Type);
                    fileString = fileString.concat("/");
                    if (med.withFood){
                        fileString = fileString.concat("1");
                    }
                    else{
                        fileString = fileString.concat("0");
                    }
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(((EveryXDaysMedication) med).times.toString());
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(String.valueOf(((EveryXDaysMedication) med).numberOfDays));
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(((EveryXDaysMedication) med).startDate.toString());
                    if (!med.prevTakenAt.isEmpty()){
                        fileString = fileString.concat("/");
                        fileString = fileString.concat(med.prevTakenAt.toString());
                    }
                }
                else if (med instanceof SpecificDayMedication){
                    fileString = fileString.concat("SpecificDay\n");
                    fileString = fileString.concat(med.name);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Strength);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(String.valueOf(med.numLeft));
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Type);
                    fileString = fileString.concat("/");
                    if (med.withFood){
                        fileString = fileString.concat("1");
                    }
                    else{
                        fileString = fileString.concat("0");
                    }
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(((SpecificDayMedication) med).Times.toString());
                    if (!med.prevTakenAt.isEmpty()){
                        fileString = fileString.concat("/");
                        fileString = fileString.concat(med.prevTakenAt.toString());
                    }
                }
                else if (med instanceof WeeklyMedication){
                    fileString = fileString.concat("Weekly\n");
                    fileString = fileString.concat(med.name);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Strength);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(String.valueOf(med.numLeft));
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Type);
                    fileString = fileString.concat("/");
                    if (med.withFood){
                        fileString = fileString.concat("1");
                    }
                    else{
                        fileString = fileString.concat("0");
                    }
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(((WeeklyMedication) med).times.toString());
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(((WeeklyMedication) med).Day);
                    if (!med.prevTakenAt.isEmpty()){
                        fileString = fileString.concat("/");
                        fileString = fileString.concat(med.prevTakenAt.toString());
                    }
                }
                else if (med instanceof MonthlyMedication){
                    fileString = fileString.concat("Monthly\n");
                    fileString = fileString.concat(med.name);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Strength);
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(String.valueOf(med.numLeft));
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(med.Type);
                    fileString = fileString.concat("/");
                    if (med.withFood){
                        fileString = fileString.concat("1");
                    }
                    else{
                        fileString = fileString.concat("0");
                    }
                    fileString = fileString.concat("/");
                    fileString = fileString.concat(String.valueOf(((MonthlyMedication) med).dayOfMonth));
                    if (!med.prevTakenAt.isEmpty()){
                        fileString = fileString.concat("/");
                        fileString = fileString.concat(med.prevTakenAt.toString());
                    }
                }
                fileString = fileString.concat("\n");
            }
            fileString = fileString.concat("NEXTMEDS\n");
            for (Medication med: nextMedList
                 ) {
                fileString = fileString.concat(med.toString());
                fileString = fileString.concat("/");
            }
            byte[] fileContent = fileString.getBytes(StandardCharsets.UTF_8);
            OutputStream outputStream = encryptedFile.openFileOutput();
            outputStream.write(fileContent);
            outputStream.flush();
            outputStream.close();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//Adds new medications
    void newMed(Medication med){
        MedicationList.add(med);
        NextMeds();
        writeToFile();
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
