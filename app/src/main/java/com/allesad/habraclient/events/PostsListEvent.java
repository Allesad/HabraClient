package com.allesad.habraclient.events;

import com.allesad.habraclient.database.models.posts.Post;
import com.allesad.habraclient.model.posts.PostListItemData;
import com.allesad.habraclient.utils.Enums;

import java.util.List;

/**
 * Created by Sakhno on 9/3/2014.
 */
public class PostsListEvent {

    private Enums.PostsListType mType;
    private List<Post> mPosts;

    public PostsListEvent(Enums.PostsListType type, List<Post> posts){
        mType = type;
        mPosts = posts;
    }

    public List<Post> getData(){
        return mPosts;
    }

    public Enums.PostsListType getType(){
        return mType;
    }
}
