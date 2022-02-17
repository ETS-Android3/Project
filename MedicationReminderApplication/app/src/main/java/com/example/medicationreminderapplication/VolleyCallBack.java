package com.example.medicationreminderapplication;

import org.json.JSONObject;

public interface VolleyCallBack {
    void onSuccess(JSONObject information);
    void onFail();
}
