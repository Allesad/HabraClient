package com.allesad.habraclient.model.posts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allesad.habraclient.R;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by Allesad on 05.04.2014.
 */
public class PostCardHeader extends CardHeader {

    private PostListItemData mPost;

    public PostCardHeader(Context context, PostListItemData post) {
        super(context, R.layout.view_post_header_inner_layout);

        mPost = post;
    }

    private PostCardHeader(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (mPost != null){
            TextView date = (TextView) view.findViewById(R.id.postHeaderInnerView_date);
            TextView author = (TextView) view.findViewById(R.id.postHeaderInnerView_author);
            TextView title = (TextView) view.findViewById(R.id.postHeaderInnerView_title);
            TextView hubs = (TextView) view.findViewById(R.id.postHeaderInnerView_hubs);

            date.setText(mPost.getDateFormatted());
            author.setText(mPost.getAuthor());
            title.setText(mPost.getTitle());
            hubs.setText(mPost.getHubs());
        }
    }
}
