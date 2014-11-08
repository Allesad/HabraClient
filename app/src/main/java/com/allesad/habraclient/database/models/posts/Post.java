package com.allesad.habraclient.database.models.posts;

import com.allesad.habraclient.interfaces.models.posts.IPost;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/20/2014 8:00 AM.
 */
@DatabaseTable(tableName = "posts")
public class Post implements IPost
{
    //=============================================================
    // Database fields
    //=============================================================

    @DatabaseField(id = true, canBeNull = false)
    public long id;

    @DatabaseField
    public Long dateCreated;

    @DatabaseField
    public String url;

    @DatabaseField
    public String title;

    @DatabaseField
    public String titleImage;

    @DatabaseField
    public String habracut;

    @DatabaseField
    public String content;

    @DatabaseField
    public String author;

    @DatabaseField
    public String authorUrl;

    @DatabaseField
    public String originalAuthor;

    @DatabaseField
    public String originalUrl;

    @DatabaseField
    public Long date;

    @DatabaseField
    public Integer viewsCount;

    @DatabaseField
    public Integer favoritesCount;

    @DatabaseField
    public Integer commentsCount;

    @DatabaseField
    public Integer newCommentsCount;

    @DatabaseField
    public Integer rating;

    @ForeignCollectionField
    public Collection<Comment> comments;

    //=============================================================
    // Empty constructor
    //=============================================================

    public Post(){}

    //=============================================================
    // IPost
    //=============================================================

    @Override
    public long getPost_id() {
        return id;
    }

    @Override
    public String getPost_url() {
        return url;
    }

    @Override
    public String getPost_title() {
        return title;
    }

    @Override
    public String getPost_titleImage() {
        return titleImage;
    }

    @Override
    public String getPost_habracut() {
        return habracut;
    }

    @Override
    public String getPost_content() {
        return null;
    }

    @Override
    public String getPost_author() {
        return author;
    }

    @Override
    public String getPost_authorUrl() {
        return authorUrl;
    }

    @Override
    public String getPost_originalAuthor() {
        return originalAuthor;
    }

    @Override
    public String getPost_originalUrl() {
        return originalUrl;
    }

    @Override
    public Long getPost_date() {
        return date;
    }

    @Override
    public Integer getPost_viewsCount() {
        return viewsCount;
    }

    @Override
    public Integer getPost_favoritesCount() {
        return favoritesCount;
    }

    @Override
    public Integer getPost_commentsCount() {
        return commentsCount;
    }

    @Override
    public Integer getPost_newCommentsCount() {
        return newCommentsCount;
    }

    @Override
    public Integer getPost_rating() {
        return rating;
    }

    @Override
    public Collection<Comment> getPost_comments() {
        return comments;
    }

    @Override
    public void applyFields(IPost source) {

    }

    //=============================================================
    // Static clone getter
    //=============================================================

    public static Post copy(IPost source){
        if (source == null){
            return null;
        }

        Post copy = new Post();
        copy.id = source.getPost_id();
        copy.url = source.getPost_url();
        copy.title = source.getPost_title();
        copy.titleImage = source.getPost_titleImage();
        copy.habracut = source.getPost_habracut();
        copy.content = source.getPost_content();
        copy.author = source.getPost_author();
        copy.authorUrl = source.getPost_authorUrl();
        copy.originalAuthor = source.getPost_originalAuthor();
        copy.originalUrl = source.getPost_originalUrl();
        copy.date = source.getPost_date();
        copy.viewsCount = source.getPost_viewsCount();
        copy.favoritesCount = source.getPost_favoritesCount();
        copy.commentsCount = source.getPost_commentsCount();
        copy.newCommentsCount = source.getPost_newCommentsCount();
        copy.rating = source.getPost_rating();
        copy.comments = source.getPost_comments();

        return copy;
    }
}
