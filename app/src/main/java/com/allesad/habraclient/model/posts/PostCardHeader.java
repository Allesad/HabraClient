package com.allesad.habraclient.model.posts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.interfaces.models.posts.IPost;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by Allesad on 05.04.2014.
 */
public class PostCardHeader extends CardHeader {

    private IPost mPost;

    public PostCardHeader(Context context, IPost post) {
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

            date.setText("Сегодня в 16:30");
            author.setText(mPost.getPost_author());
            title.setText(mPost.getPost_title());
            hubs.setText("Hubs");
        }
    }
}
