package com.allesad.habraclient.robospice.requests;

import com.allesad.habraclient.helpers.RequestHelper;
import com.allesad.habraclient.model.posts.PostListItemData;
import com.allesad.habraclient.robospice.response.posts.PostsListResponse;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;

/**
 * Created by Allesad on 25.03.2014.
 */
public class PostsListRequest extends SpiceRequest<PostsListResponse> {

    private String mUrl;
    private int mPage;

    public PostsListRequest(String url, int page) {
        super(PostsListResponse.class);

        mUrl = url;
        mPage = page;
    }

    @Override
    public PostsListResponse loadDataFromNetwork() throws Exception {
        String readyUrl = mUrl.replace("%page%", String.valueOf(mPage));
        PostsListResponse response;

        try {
            response = RequestHelper.getPosts(readyUrl);
        }catch (IOException ex){
            return null;
        }

        return response;
    }
}
