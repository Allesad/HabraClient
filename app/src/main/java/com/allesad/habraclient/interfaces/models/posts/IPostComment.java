package com.allesad.habraclient.interfaces.models.posts;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/20/2014 8:59 AM.
 */
public interface IPostComment {

    long getComment_id();
    Long getComment_parentCommentId();
    IPost getComment_parentPost();
    String getComment_url();
    String getComment_author();
    String getComment_authorUrl();
    String getComment_avatar();
    Integer getComment_rating();
    String getComment_content();
    Long getComment_date();

}
