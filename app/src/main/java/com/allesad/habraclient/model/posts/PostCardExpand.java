package com.allesad.habraclient.model.posts;

import android.content.Context;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Allesad on 05.04.2014.
 */
public class PostCardExpand extends CardExpand {

    private PostListItemData mPost;

    public PostCardExpand(Context context, PostListItemData post) {
        super(context);

        mPost = post;
    }

    private PostCardExpand(Context context, int innerLayout) {
        super(context, innerLayout);
    }
}
