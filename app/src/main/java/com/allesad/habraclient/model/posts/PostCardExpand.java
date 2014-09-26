package com.allesad.habraclient.model.posts;

import android.content.Context;

import com.allesad.habraclient.interfaces.models.posts.IPost;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Allesad on 05.04.2014.
 */
public class PostCardExpand extends CardExpand {

    private IPost mPost;

    public PostCardExpand(Context context, IPost post) {
        super(context);

        mPost = post;
    }

    private PostCardExpand(Context context, int innerLayout) {
        super(context, innerLayout);
    }
}
