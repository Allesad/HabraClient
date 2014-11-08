package com.allesad.habraclient.robospice.response.posts;

import com.allesad.habraclient.model.posts.CommentListItemData;
import com.allesad.habraclient.robospice.response.BaseResponse;

import java.util.List;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 10/25/2014 11:35 PM.
 */
public class CommentsListResponse extends BaseResponse {

    //=============================================================
    // Variables
    //=============================================================

    private List<CommentListItemData> mComments;

    public List<CommentListItemData> getComments(){
        return mComments;
    }

    public void setComments(List<CommentListItemData> comments){
        mComments = comments;
    }
}
