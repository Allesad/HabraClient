package com.allesad.habraclient.model.posts;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allesad.habraclient.R;
import com.allesad.habraclient.database.models.posts.Post;
import com.allesad.habraclient.helpers.IntentHelper;
import com.allesad.habraclient.utils.Utils;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by Allesad on 25.03.2014.
 */
public class PostCard extends Card {

    private Post mPost;

    public PostCard(Context context, Post post) {
        super(context, R.layout.view_post_card_inner_layout);

        mPost = post;

        init();
    }

    private PostCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (mPost != null){
            TextView habracut = (TextView) view.findViewById(R.id.postInnerView_habracut);
            TextView rating = (TextView) view.findViewById(R.id.postInnerView_rating);
            TextView views = (TextView) view.findViewById(R.id.postInnerView_views);
            TextView favorites = (TextView) view.findViewById(R.id.postInnerView_favorites);
            TextView comments = (TextView) view.findViewById(R.id.postInnerView_comments);

            habracut.setText(mPost.getPost_habracut());
            rating.setText(Utils.parseRating(mPost.getPost_rating()));
            views.setText(String.valueOf(mPost.getPost_viewsCount()));
            favorites.setText(String.valueOf(mPost.getPost_favoritesCount()));
            String commentsText = String.valueOf(mPost.getPost_commentsCount());
            if (mPost.getPost_newCommentsCount() > 0){
                commentsText += "+" + mPost.getPost_newCommentsCount();
            }
            comments.setText(commentsText);
        }
    }

    public long getPostId(){
        return mPost != null ? mPost.getPost_id() : 0;
    }

    private void init(){
        PostCardHeader header = new PostCardHeader(getContext(), mPost);
        addCardHeader(header);

        if (!TextUtils.isEmpty(mPost.getPost_titleImage())){
            PostCardThumb thumb = new PostCardThumb(getContext(), mPost);
            thumb.setExternalUsage(true);
            addCardThumbnail(thumb);
        }

        PostCardExpand expand = new PostCardExpand(getContext(), mPost);
        addCardExpand(expand);

        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                if (mPost != null){
                    IntentHelper.toPostContentScreen(getContext(), mPost.getPost_id());
                }
            }
        });
    }
}
