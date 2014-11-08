package com.allesad.habraclient.adapters.posts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allesad.habraclient.fragments.posts.PostCommentsFragment;
import com.allesad.habraclient.fragments.posts.PostContentFragment;
import com.allesad.habraclient.model.posts.PostContentData;

/**
 * Created by Allesad on 09.04.2014.
 */
public class PostContentPagerAdapter extends FragmentStatePagerAdapter {

    private final static int TAB_CONTENT = 0;
    private final static int TAB_COMMENTS = 1;

    private int mPostId;
    private PostContentData mPost;

    public PostContentPagerAdapter(FragmentManager fm, PostContentData post, int postId) {
        super(fm);

        mPostId = postId;
        mPost = post;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case TAB_CONTENT:
                return PostContentFragment.newInstance(mPost);
            case TAB_COMMENTS:
                return PostCommentsFragment.newInstance(mPost, mPostId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
