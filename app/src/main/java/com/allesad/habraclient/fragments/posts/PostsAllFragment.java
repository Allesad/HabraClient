package com.allesad.habraclient.fragments.posts;

import com.allesad.habraclient.utils.Enums;

/**
 * Created by Allesad on 14.04.2014.
 */
public class PostsAllFragment extends BasePostsListFragment
{
    //=============================================================
    // Constants
    //=============================================================
    private final static String URL = "http://habrahabr.ru/feed/all/page%page%";

    //=============================================================
    // Variables
    //=============================================================
    private int mPage;

    //=============================================================
    // Constructor
    //=============================================================

    public PostsAllFragment(){
        mPage = 1;
    }

    //=============================================================
    // Abstract methods implementation
    //=============================================================

    @Override
    protected Enums.PostsListType getType() {
        return Enums.PostsListType.ALL;
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
