package com.allesad.habraclient.events;

import com.allesad.habraclient.model.posts.PostContentData;

/**
 * Created by Sakhno on 8/24/2014.
 */
public class PostEvent {

    private PostContentData mPost;

    public PostEvent(PostContentData post){
        mPost = post;
    }

    public PostContentData getPost(){
        return mPost;
    }
}
