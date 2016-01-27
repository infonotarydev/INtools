package com.infonotary.INtools.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonBooleanTools {
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
            return true;
        } catch (JSONException ex) {
            return false;
        }
    }

    public static boolean isJSONArrayValid(String test) {
        try {
            new JSONArray(test);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}