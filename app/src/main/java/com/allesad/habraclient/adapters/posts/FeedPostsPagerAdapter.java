package com.allesad.habraclient.adapters.posts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.fragments.posts.PostsAllFragment;
import com.allesad.habraclient.fragments.posts.PostsInterestingFragment;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/26/2014 8:11 PM.
 */
public class FeedPostsPagerAdapter extends FragmentPagerAdapter
{
    //=============================================================
    // Constants
    //=============================================================

    private final static int TAB_INTERESTING = 0;
    private final static int TAB_ALL = 1;

    public FeedPostsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case TAB_INTERESTING:
                return new PostsInterestingFragment();
            case TAB_ALL:
                return new PostsAllFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case TAB_INTERESTING:
                return HabraClientApplication.getAppContext().getString(R.string.tab_interesting);
            case TAB_ALL:
                return HabraClientApplication.getAppContext().getString(R.string.tab_all);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
