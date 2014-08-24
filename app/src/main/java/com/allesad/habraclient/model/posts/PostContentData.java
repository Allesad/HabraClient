package com.allesad.habraclient.model.posts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allesad on 08.04.2014.
 */
public class PostContentData extends PostListItemData {

    private String content;

    private List<CommentListItemData> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentListItemData> getComments() {
        if (comments == null){
            comments = new ArrayList<CommentListItemData>();
        }
        return comments;
    }

    public void setComments(List<CommentListItemData> comments) {
        this.comments = comments;
    }
}
