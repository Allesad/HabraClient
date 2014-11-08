package com.allesad.habraclient.model.navigation;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 11/8/2014 6:45 PM.
 */
public class NavigationItem {

    //=============================================================
    // Variables
    //=============================================================

    private int mId;

    private int mTitleResId;

    private int mIconResId;

    //=============================================================
    // Constructor
    //=============================================================

    public NavigationItem(int id, int titleResId, int iconResId){
        mId = id;
        mTitleResId = titleResId;
        mIconResId = iconResId;
    }

    //=============================================================
    // Getters
    //=============================================================

    public int getId() {
        return mId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getIconResId() {
        return mIconResId;
    }
}
