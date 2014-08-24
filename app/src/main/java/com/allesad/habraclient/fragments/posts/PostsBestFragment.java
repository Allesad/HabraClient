package com.allesad.habraclient.fragments.posts;

/**
 * Created by Allesad on 26.03.2014.
 */
public class PostsBestFragment extends BasePostsListFragment {

    private final static String URL = "http://habrahabr.ru/posts/top/daily/page%page%";

    private int mPage;

    public PostsBestFragment(){
        mPage = 1;
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
