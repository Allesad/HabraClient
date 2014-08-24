package com.allesad.habraclient.adapters.posts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.allesad.habraclient.fragments.posts.PostsBestFragment;
import com.allesad.habraclient.fragments.posts.PostsCorporativeFragment;
import com.allesad.habraclient.fragments.posts.PostsThematicFragment;

/**
 * Created by Allesad on 14.04.2014.
 */
public class PostsListsPagerAdapter extends FragmentPagerAdapter {

    private final static int TAB_BEST = 0;
    private final static int TAB_THEMATIC = 1;
    private final static int TAB_CORPORATIVE = 2;

    public PostsListsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case TAB_BEST:
                return new PostsBestFragment();
            case TAB_THEMATIC:
                return new PostsThematicFragment();
            case TAB_CORPORATIVE:
                return new PostsCorporativeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
