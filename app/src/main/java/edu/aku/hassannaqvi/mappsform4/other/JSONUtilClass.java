package edu.aku.hassannaqvi.mappsform4.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtilClass {
    public static <T> T getModelFromJSON(String json, Class<T> type) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, type);
    }
}
