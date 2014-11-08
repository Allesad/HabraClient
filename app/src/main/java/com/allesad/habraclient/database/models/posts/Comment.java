package com.allesad.habraclient.database.models.posts;

import com.allesad.habraclient.interfaces.models.posts.IPost;
import com.allesad.habraclient.interfaces.models.posts.IPostComment;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/20/2014 8:00 AM.
 */
@DatabaseTable(tableName = "post_comments")
public class Comment implements IPostComment
{
    //=============================================================
    // Database fields
    //=============================================================

    @DatabaseField(id = true, canBeNull = false)
    public long id;

    @DatabaseField(canBeNull = false)
    public Long parentCommentId;

    @DatabaseField(canBeNull = false, foreign = true)
    public Post parentPost;

    @DatabaseField
    public String url;

    @DatabaseField
    public String author;

    @DatabaseField
    public String authorUrl;

    @DatabaseField
    public String avatar;

    @DatabaseField
    public Integer rating;

    @DatabaseField
    public String content;

    @DatabaseField
    public Long date;

    //=============================================================
    // IPostComment
    //=============================================================

    @Override
    public long getComment_id() {
        return id;
    }

    @Override
    public Long getComment_parentCommentId() {
        return parentCommentId;
    }

    @Override
    public IPost getComment_parentPost() {
        return parentPost;
    }

    @Override
    public String getComment_url() {
        return url;
    }

    @Override
    public String getComment_author() {
        return author;
    }

    @Override
    public String getComment_authorUrl() {
        return authorUrl;
    }

    @Override
    public String getComment_avatar() {
        return avatar;
    }

    @Override
    public Integer getComment_rating() {
        return rating;
    }

    @Override
    public String getComment_content() {
        return content;
    }

    @Override
    public Long getComment_date() {
        return date;
    }
}
