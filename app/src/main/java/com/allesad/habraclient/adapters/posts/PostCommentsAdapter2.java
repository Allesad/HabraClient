package com.allesad.habraclient.adapters.posts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.components.HtmlTagHandler;
import com.allesad.habraclient.components.WebViewExtended;
import com.allesad.habraclient.model.posts.CommentListItemData;
import com.allesad.habraclient.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Allesad on 13.04.2014.
 */
public class PostCommentsAdapter2 extends RecyclerView.Adapter<PostCommentsAdapter2.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CommentListItemData> mComments;

    public PostCommentsAdapter2(Context context, List<CommentListItemData> comments) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mComments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.list_item_comment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        CommentListItemData comment = mComments.get(i);

        ImageLoader.getInstance().displayImage(comment.getAvatarUrl(), viewHolder.avatar);
        viewHolder.author.setText(comment.getAuthor());
        viewHolder.date.setText(comment.getDateFormatted());
        if (comment.getRating() > 0){
            viewHolder.rating.setTextColor(mContext.getResources().getColor(R.color.rating_positive));
        }else if (comment.getRating() < 0){
            viewHolder.rating.setTextColor(mContext.getResources().getColor(R.color.rating_negative));
        }else{
            viewHolder.rating.setTextColor(mContext.getResources().getColor(R.color.light_text_color));
        }
        viewHolder.rating.setText(Utils.parseRating(comment.getRating()));

        if (comment.isShowFullContent()){
            viewHolder.content.setVisibility(View.GONE);
            viewHolder.fullContent.setVisibility(View.VISIBLE);
            viewHolder.fullContent.loadComment(comment);
        }else{
            viewHolder.fullContent.setVisibility(View.GONE);
            viewHolder.content.setVisibility(View.VISIBLE);
            viewHolder.content.setText(Html.fromHtml(comment.getContent(), new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String s) {
                    Drawable image = mContext.getResources().getDrawable(R.drawable.ic_no_image);
                    image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
                    return image;
                }
            }, new HtmlTagHandler()));
        }
    }

    @Override
    public int getItemCount() {
        return mComments != null ? mComments.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView author;
        private TextView date;
        private TextView rating;
        private TextView content;
        private WebViewExtended fullContent;

        public ViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.commentView_avatar);
            author = (TextView) itemView.findViewById(R.id.commentView_author);
            date = (TextView) itemView.findViewById(R.id.commentView_date);
            rating = (TextView) itemView.findViewById(R.id.commentView_rating);
            content = (TextView) itemView.findViewById(R.id.commentView_content);
            fullContent = (WebViewExtended) itemView.findViewById(R.id.commentView_full_content);
        }
    }
}
