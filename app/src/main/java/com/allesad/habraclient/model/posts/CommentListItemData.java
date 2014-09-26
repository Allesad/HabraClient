package com.allesad.habraclient.model.posts;

import android.text.format.DateFormat;

import java.io.Serializable;

/**
 * Created by Allesad on 08.04.2014.
 */
public class CommentListItemData implements Serializable {

    private int commentId;
    private int parentCommentId;
    private int parentPostId;
    private String commentUrl;
    private String author;
    private String authorUrl;
    private String avatarUrl;
    private int rating;
    private String content;
    private long date;
    private int level;

    private boolean showFullContent;

    public CommentListItemData(){
        this.commentId = 0;
        this.parentCommentId = 0;
        this.parentPostId = 0;
        this.commentUrl = "";
        this.author = "";
        this.authorUrl = "";
        this.avatarUrl = "";
        this.rating = 0;
        this.content = "";
        this.showFullContent = false;
        this.date = System.currentTimeMillis();
        this.level = 0;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public int getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(int parentPostId) {
        this.parentPostId = parentPostId;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public boolean isShowFullContent() {
        return showFullContent;
    }

    public void setShowFullContent(boolean showFullContent) {
        this.showFullContent = showFullContent;
    }

    public CharSequence getDateFormatted(){
        return DateFormat.format("d MMMM yyyy H:mm", date);
    }
}
