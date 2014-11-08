package com.allesad.habraclient.fragments.posts;

import com.allesad.habraclient.utils.Enums;

/**
 * Created by Allesad on 06.04.2014.
 */
public class PostsInterestingFragment extends BasePostsListFragment
{
    //=============================================================
    // Constants
    //=============================================================
    private final static String URL = "http://habrahabr.ru/feed/interesting/page%page%";

    //=============================================================
    // Variables
    //=============================================================
    private int mPage;

    //=============================================================
    // Constructor
    //=============================================================

    public PostsInterestingFragment(){
        mPage = 1;
    }

    //=============================================================
    // Abstract methods implementation
    //=============================================================

    @Override
    protected Enums.PostsListType getType() {
        return Enums.PostsListType.INTERESTING;
    }

    @Override
    protected String getUrl() {
        return URL;
    }

    @Override
    protected int getPage() {
        return mPage;
    }

    @Override
    protected void setPage(int page) {
        mPage = page;
    }
}
