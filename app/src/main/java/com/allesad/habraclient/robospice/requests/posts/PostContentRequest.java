package com.allesad.habraclient.robospice.requests.posts;

import com.allesad.habraclient.helpers.RequestHelper;
import com.allesad.habraclient.robospice.response.posts.PostContentResponse;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;

/**
 * Created by Allesad on 08.04.2014.
 */
public class PostContentRequest extends SpiceRequest<PostContentResponse> {

    private int mPostId;

    public PostContentRequest(int postId) {
        super(PostContentResponse.class);

        mPostId = postId;
    }

    @Override
    public PostContentResponse loadDataFromNetwork() throws Exception {
        PostContentResponse response;

        try {
            response = RequestHelper.getPost(mPostId);
        }catch (IOException ex){
            response = new PostContentResponse();
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }
}
