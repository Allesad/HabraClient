package com.allesad.habraclient.model.posts;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allesad.habraclient.R;
import com.allesad.habraclient.helpers.IntentHelper;
import com.allesad.habraclient.utils.Utils;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by Allesad on 25.03.2014.
 */
public class PostCard extends Card {

    private PostListItemData mPost;

    public PostCard(Context context, PostListItemData post) {
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

            habracut.setText(mPost.getHabracut());
            rating.setText(Utils.parseRating(mPost.getRating()));
            views.setText(String.valueOf(mPost.getViews()));
            favorites.setText(String.valueOf(mPost.getFavoritesCount()));
            String commentsText = String.valueOf(mPost.getCommentsCount());
            if (mPost.getNewCommentsCount() > 0){
                commentsText += "+ " + mPost.getNewCommentsCount();
            }
            comments.setText(commentsText);
        }
    }

    public int getPostId(){
        return mPost != null ? mPost.getId() : 0;
    }

    private void init(){
        PostCardHeader header = new PostCardHeader(getContext(), mPost);
        addCardHeader(header);

        if (!TextUtils.isEmpty(mPost.getImage())){
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
                    IntentHelper.toPostContentScreen(getContext(), mPost.getId());
                }
            }
        });
    }
}
