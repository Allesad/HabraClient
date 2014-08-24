package com.allesad.habraclient.model.posts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by Allesad on 05.04.2014.
 */
public class PostCardThumb extends CardThumbnail {

    private PostListItemData mPost;

    public PostCardThumb(Context context, PostListItemData post) {
        super(context);

        mPost = post;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View imageView) {
        if (mPost != null){
            ImageLoader.getInstance().displayImage(mPost.getImage(), (android.widget.ImageView) imageView);
        }
    }
}
