package com.allesad.habraclient.fragments.posts;

/**
 * Created by Allesad on 14.04.2014.
 */
public class PostsCorporativeFragment extends BasePostsListFragment {

    private final static String URL = "http://habrahabr.ru/posts/corporative/new/page%page%";
    private int mPage;

    public PostsCorporativeFragment(){
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
