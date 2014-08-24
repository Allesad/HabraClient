package com.allesad.habraclient.adapters.posts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.allesad.habraclient.fragments.posts.PostCommentsFragment;
import com.allesad.habraclient.fragments.posts.PostContentFragment;

/**
 * Created by Allesad on 09.04.2014.
 */
public class PostContentPagerAdapter extends FragmentPagerAdapter {

    private final static int TAB_CONTENT = 0;
    private final static int TAB_COMMENTS = 1;

    public PostContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case TAB_CONTENT:
                return PostContentFragment.newInstance();
            case TAB_COMMENTS:
                return PostCommentsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
