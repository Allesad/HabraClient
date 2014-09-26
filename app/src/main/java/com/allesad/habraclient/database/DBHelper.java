package com.allesad.habraclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.database.dao.posts.PostDao;
import com.allesad.habraclient.database.dao.posts.PostDaoImpl;
import com.allesad.habraclient.database.models.posts.Post;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/20/2014 8:22 AM.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper
{
    //=============================================================
    // Constants
    //=============================================================
    private static final String DATABASE_NAME = "habraclient.db";
    private static final int DATABASE_VERSION = 2;

    //=============================================================
    // Variables
    //=============================================================

    private static DBHelper instance;

    private PostDao mPostDao;

    //=============================================================
    // Constructor
    //=============================================================

    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    public static synchronized DBHelper get(Context context){
        if (instance == null){
            instance = new DBHelper(context);
        }

        return instance;
    }

    public static synchronized DBHelper get(){
        if (instance == null){
            instance = new DBHelper(HabraClientApplication.getAppContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Post.class);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Unable to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Post.class, true);
            TableUtils.createTable(connectionSource, Post.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //=============================================================
    // Posts
    //=============================================================

    public PostDao getPostDao(){
        if (mPostDao == null){
            try {
                mPostDao = new PostDaoImpl(connectionSource, Post.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mPostDao;
    }
}
