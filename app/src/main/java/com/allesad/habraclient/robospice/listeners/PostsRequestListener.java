package com.allesad.habraclient.robospice.listeners;

import com.allesad.habraclient.events.PostsListEvent;
import com.allesad.habraclient.robospice.response.posts.PostsListResponse;
import com.allesad.habraclient.utils.Enums;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import de.greenrobot.event.EventBus;

/**
 * Created by Sakhno on 9/3/2014.
 */
public class PostsRequestListener implements RequestListener<PostsListResponse> {

    private Enums.PostsListType mType;

    public PostsRequestListener(Enums.PostsListType type){
        mType = type;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {

    }

    @Override
    public void onRequestSuccess(PostsListResponse response) {
        EventBus.getDefault().post(new PostsListEvent(mType, response));
    }
}
