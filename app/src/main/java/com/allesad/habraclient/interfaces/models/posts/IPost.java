package com.allesad.habraclient.interfaces.models.posts;

import com.allesad.habraclient.database.models.posts.Comment;

import java.util.Collection;

/**
 * Created by Sakhno on 9/20/2014.
 */
public interface IPost {

    long getPost_id();
    String getPost_url();
    String getPost_title();
    String getPost_titleImage();
    String getPost_habracut();
    String getPost_content();
    String getPost_author();
    String getPost_authorUrl();
    String getPost_originalAuthor();
    String getPost_originalUrl();
    Long getPost_date();
    Integer getPost_viewsCount();
    Integer getPost_favoritesCount();
    Integer getPost_commentsCount();
    Integer getPost_newCommentsCount();
    Integer getPost_rating();
    Collection<Comment> getPost_comments();

    void applyFields(IPost source);
}
