package com.allesad.habraclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Allesad on 23.03.2014.
 */
public class Preferences {

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

    private static Preferences mPreferences = null;
    private static SharedPreferences mSharedPreferences;

    private Preferences(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Preferences getInstance(Context context){
        return mPreferences != null ? mPreferences : (mPreferences = new Preferences(context));
    }

    public boolean getUserLearnedDrawer(){
        return mSharedPreferences.getBoolean(USER_LEARNED_DRAWER_KEY, USER_LEARNED_DRAWER_DEFAULT);
    }
    public void setUserLearnedDrawer(boolean learned){
        mSharedPreferences.edit().putBoolean(USER_LEARNED_DRAWER_KEY, learned).apply();
    }
}
