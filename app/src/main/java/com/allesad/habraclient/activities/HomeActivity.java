package com.allesad.habraclient.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.allesad.habraclient.R;
import com.allesad.habraclient.fragments.navigation.NavigationFragment;
import com.allesad.habraclient.fragments.posts.PostsContainerFragment;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 11/8/2014 7:31 PM.
 */
public class HomeActivity extends BaseActivity implements NavigationFragment.INavigationListener
{
    //=============================================================
    // Constants
    //=============================================================

    private final static int NAVDRAWER_LAUNCH_DELAY = 250;

    //=============================================================
    // Variables
    //=============================================================

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Handler mHandler;

    //=============================================================
    // Activity lifecycle
    //=============================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainView_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        mDrawerLayout.setDrawerListener(mToggle);

        mHandler = new Handler();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PostsContainerFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //=============================================================
    // NavigationFragment.INavigationListener
    //=============================================================

    @Override
    public void onNavigationItemClicked(final int itemId) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        mDrawerLayout.closeDrawer(Gravity.START);
    }

    //=============================================================
    // Private methods
    //=============================================================

    private void goToNavDrawerItem(final int itemId){

    }
}
