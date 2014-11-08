package com.allesad.habraclient.model.posts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allesad.habraclient.interfaces.models.posts.IPost;
import com.nostra13.universalimageloader.core.ImageLoader;

import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by Allesad on 05.04.2014.
 */
public class PostCardThumb extends CardThumbnail {

    private IPost mPost;

    public PostCardThumb(Context context, IPost post) {
        super(context);

        mPost = post;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View imageView) {
        if (mPost != null){
            ImageLoader.getInstance().displayImage(mPost.getPost_titleImage(), (android.widget.ImageView) imageView);
        }
    }
}
