package com.allesad.habraclient.adapters.posts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class PostCommentsAdapter extends ArrayAdapter<CommentListItemData> {

    private LayoutInflater mInflater;
    private List<CommentListItemData> mComments;

    public PostCommentsAdapter(Context context, List<CommentListItemData> comments) {
        super(context, 0);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mComments = comments;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public CommentListItemData getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mComments.get(position).getCommentId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentListItemData comment = getItem(position);
        ViewHolder holder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_comment, null);

            holder = new ViewHolder();
            assert convertView != null;
            holder.avatar = (ImageView) convertView.findViewById(R.id.commentView_avatar);
            holder.author = (TextView) convertView.findViewById(R.id.commentView_author);
            holder.date = (TextView) convertView.findViewById(R.id.commentView_date);
            holder.rating = (TextView) convertView.findViewById(R.id.commentView_rating);
            holder.content = (TextView) convertView.findViewById(R.id.commentView_content);
            holder.fullContent = (WebViewExtended) convertView.findViewById(R.id.commentView_full_content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(comment.getAvatarUrl(), holder.avatar);
        holder.author.setText(comment.getAuthor());
        holder.date.setText(comment.getDateFormatted());
        if (comment.getRating() > 0){
            holder.rating.setTextColor(getContext().getResources().getColor(R.color.rating_positive));
        }else if (comment.getRating() < 0){
            holder.rating.setTextColor(getContext().getResources().getColor(R.color.rating_negative));
        }else{
            holder.rating.setTextColor(getContext().getResources().getColor(R.color.light_text_color));
        }
        holder.rating.setText(Utils.parseRating(comment.getRating()));

        if (comment.isShowFullContent()){
            holder.content.setVisibility(View.GONE);
            holder.fullContent.setVisibility(View.VISIBLE);
            holder.fullContent.loadComment(comment);
        }else{
            holder.fullContent.setVisibility(View.GONE);
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(Html.fromHtml(comment.getContent(), new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String s) {
                    Drawable image = getContext().getResources().getDrawable(R.drawable.ic_no_image);
                    image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
                    return image;
                }
            }, new HtmlTagHandler()));
        }

        return convertView;
    }

    private class ViewHolder {
        private ImageView avatar;
        private TextView author;
        private TextView date;
        private TextView rating;
        private TextView content;
        private WebViewExtended fullContent;
    }
}
