package com.allesad.habraclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Allesad on 05.04.2014.
 */
public class Utils {

    public final static String CLIENT_ID = "05329ae5cc5bbb2.88812457";
    public final static String TOKEN = "a2c91e82f808bd509bbf064319cabc41964bbe78";
    public final static boolean DEBUG = true;

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isAvailable()
                && manager.getActiveNetworkInfo().isConnected())
            return true;

        return false;
    }

    public static String arrayToString(String[] array, String delimiter) {
        StringBuilder arTostr = new StringBuilder();
        if (array.length > 0) {
            arTostr.append(array[0]);
            for (int i=1; i<array.length; i++) {
                arTostr.append(delimiter);
                arTostr.append(array[i]);
            }
        }
        return arTostr.toString();
    }

    public static String parseRating(Integer rating){
        if (rating == null){
            return "-";
        }
        String ratingText;
        if (rating > 0){
            ratingText = "+" + rating;
        }else{
            ratingText = String.valueOf(rating);
        }

        return ratingText;
    }
}
