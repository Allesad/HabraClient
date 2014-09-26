package com.allesad.habraclient.fragments.posts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.posts.FeedPostsPagerAdapter;
import com.allesad.habraclient.components.ViewPagerExtended;
import com.allesad.habraclient.components.views.SlidingTabLayout;

/**
 * Created by Allesad on 14.04.2014.
 */
public class PostsContainerFragment extends Fragment {

    //=============================================================
    // Variables
    //=============================================================
    private FeedPostsPagerAdapter mAdapter;

    //=============================================================
    // Fragment lifecycle
    //=============================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FeedPostsPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerExtended viewPager = (ViewPagerExtended) view.findViewById(R.id.pager);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);

        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }
}
