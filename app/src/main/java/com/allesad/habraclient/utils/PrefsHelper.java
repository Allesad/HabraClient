package com.allesad.habraclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.allesad.habraclient.HabraClientApplication;

/**
 * Created by Allesad on 23.03.2014.
 */
public class PrefsHelper {

    //=============================================================
    // Constants
    //=============================================================
    /**
     * Remember the position of the selected item.
     */
    private static final String MENU_SELECTED_POSITION_KEY = "selected_navigation_drawer_position";
    private static final int MENU_SELECTED_POSITION_DEFAULT = 0;

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String USER_LEARNED_DRAWER_KEY = "navigation_drawer_learned";
    private static final boolean USER_LEARNED_DRAWER_DEFAULT = false;

    public static final String PREFS_PHPSESSION_ID_KEY = "phpSessionIdKey";
    public static final String HSEC_ID_KEY = "hsecIdKey";

    public static final String DEFAULT_POSTS_CACHE_COUNT_KEY = "defaultPostsCacheCountKey";

    //=============================================================
    // Variables
    //=============================================================

    private static PrefsHelper mPrefsHelper = null;
    private static SharedPreferences mSharedPreferences;

    //=============================================================
    // Constructor
    //=============================================================

    private PrefsHelper(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //=============================================================
    // Public accessor
    //=============================================================

    public static PrefsHelper get(){
        return mPrefsHelper != null ? mPrefsHelper : (mPrefsHelper = new PrefsHelper(HabraClientApplication.getAppContext()));
    }

    //=============================================================
    // Public store methods
    //=============================================================

    public void storeString(String key, String value){
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public void storeBoolean(String key, boolean value){
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void storeInt(String key, int value){
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public void storeLong(String key, long value){
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    //=============================================================
    // Public get methods
    //=============================================================

    public boolean getUserLearnedDrawer(){
        return mSharedPreferences.getBoolean(USER_LEARNED_DRAWER_KEY, USER_LEARNED_DRAWER_DEFAULT);
    }

    public void setUserLearnedDrawer(boolean learned){
        mSharedPreferences.edit().putBoolean(USER_LEARNED_DRAWER_KEY, learned).apply();
    }

    public String getPhpSessionId(){
        return mSharedPreferences.getString(PREFS_PHPSESSION_ID_KEY, "if9tt79rgvqr2n4li09fkegk57");
    }

    public String getHsecId(){
        return mSharedPreferences.getString(HSEC_ID_KEY, "75fe93f63cee1bf875712506dc2aa18e");
    }
}
