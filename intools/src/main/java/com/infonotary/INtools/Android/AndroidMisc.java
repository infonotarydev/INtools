package com.infonotary.INtools.Android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.infonotary.intools.BuildConfig;

import java.lang.reflect.Method;

public class AndroidMisc {

    public static String getUniqueID(Context context) {
        String serialNum;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            serialNum = (String) (get.invoke(c, "ro.serialno", "unknown"));
        } catch (Exception ignored) {
            Log.e("getUniqueID", "Falling back to android_id because could not get the serial number. Error: " + ignored.getMessage());
            serialNum = null;
        }

        if (serialNum == null || serialNum.equals("unknown")) {
            @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            String packageName = BuildConfig.APPLICATION_ID;
            return android_id + packageName;
        } else {
            String packageName = BuildConfig.APPLICATION_ID;
            return serialNum + packageName;
        }
    }

}