package com.allesad.habraclient.robospice.requests.posts;

import com.allesad.habraclient.helpers.RequestHelper;
import com.allesad.habraclient.robospice.response.posts.CommentsListResponse;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 10/25/2014 11:59 PM.
 */
public class PostCommentsRequest extends SpiceRequest<CommentsListResponse>
{
    private int mPostId;

    public PostCommentsRequest(int postId) {
        super(CommentsListResponse.class);

        mPostId = postId;
    }

    @Override
    public CommentsListResponse loadDataFromNetwork() throws Exception {
        CommentsListResponse response;

        try {
            response = RequestHelper.getComments(mPostId);
        }catch (IOException ex){
            response = new CommentsListResponse();
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }
}
