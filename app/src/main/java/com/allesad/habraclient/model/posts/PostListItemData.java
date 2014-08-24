package com.allesad.habraclient.model.posts;

import java.io.Serializable;

/**
 * Created by Allesad on 25.03.2014.
 */
public class PostListItemData implements Serializable {

    private int id;
    private String title;
    private String url;
    private String image;
    private String habracut;
    private String author;
    private String authorUrl;
    private String originalAuthor;
    private String originalUrl;
    private long date;
    private String hubs;
    private int views;
    private int favoritesCount;
    private int commentsCount;
    private int newCommentsCount;
    private int rating;

    public PostListItemData(){
        this.id = 0;
        this.title = "";
        this.url = "";
        this.image = "";
        this.habracut = "";
        this.author = "";
        this.authorUrl = "";
        this.date = 0;
        this.hubs = "";
        this.views = 0;
        this.favoritesCount = 0;
        this.commentsCount = 0;
        this.newCommentsCount = 0;
        this.rating = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHabracut() {
        return habracut;
    }

    public void setHabracut(String habracut) {
        this.habracut = habracut;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public void setOriginalAuthor(String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getHubs() {
        return hubs;
    }

    public void setHubs(String hubs) {
        this.hubs = hubs;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getNewCommentsCount() {
        return newCommentsCount;
    }

    public void setNewCommentsCount(int newCommentsCount) {
        this.newCommentsCount = newCommentsCount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDateFormatted(){
        // TODO: add actual date formatting
        return "Сегодня в 16:30";
    }
}
