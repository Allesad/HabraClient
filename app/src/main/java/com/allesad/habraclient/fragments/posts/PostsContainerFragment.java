package com.allesad.habraclient.fragments.posts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.posts.PostsListsPagerAdapter;
import com.allesad.habraclient.components.ViewPagerExtended;

/**
 * Created by Allesad on 14.04.2014.
 */
public class PostsContainerFragment extends Fragment {

    private ViewPagerExtended mViewPager;
    private PostsListsPagerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPagerExtended) view.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new PostsListsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }
}
