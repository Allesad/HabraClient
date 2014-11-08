package com.allesad.habraclient.robospice.response.posts;

import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.robospice.response.BaseResponse;

/**
 * Created by Allesad on 08.04.2014.
 */
public class PostContentResponse extends BaseResponse {

    private PostContentData post;

    public PostContentResponse(){

    }

    public PostContentData getPost() {
        return post;
    }

    public void setPost(PostContentData post) {
        this.post = post;
    }
}
