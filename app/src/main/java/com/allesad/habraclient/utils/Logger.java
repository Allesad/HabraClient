package com.allesad.habraclient.utils;

import android.util.Log;

/**
 * Created by Allesad on 09.04.2014.
 */
public class Logger {

    private final static String APP_TAG = "habraclient";

    public static void v(String message){
        if (Utils.DEBUG){
            Log.v(APP_TAG, message);
        }
    }

    public static void d(String message){
        if (Utils.DEBUG){
            Log.d(APP_TAG, message);
        }
    }
}
