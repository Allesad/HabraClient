package com.allesad.habraclient.helpers;

import com.allesad.habraclient.model.posts.CommentListItemData;
import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.model.posts.PostListItemData;
import com.allesad.habraclient.robospice.response.posts.PostContentResponse;
import com.allesad.habraclient.robospice.response.posts.PostsListResponse;
import com.allesad.habraclient.utils.Logger;
import com.allesad.habraclient.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Allesad on 23.03.2014.
 */
public class RequestHelper {

    private final static String BASE_URL = "http://habrahabr.ru/";
    //private final static String BASE_URL = "178.248.233.33/";

    public static PostsListResponse getPosts(String url) throws IOException {
        PostsListResponse response = new PostsListResponse();

        Document document = Jsoup.connect(url)
                .get();

        Elements posts = document.select("div.post");

        for(Element post : posts){
            PostListItemData postData  = new PostListItemData();

            Integer postId          = Integer.parseInt(post.attr("id").replace("post_", ""));
            Element postTitle       = post.select("a.post_title").first();
            Element habracut        = post.select("div.content").first();
            Element postImage       = habracut.select("img").first();
            Element hubs            = post.select("div.hubs").first();
            //Element date            = post.select("div.published").first();
            Element originalAuthor  = post.select("div.original-author > a").first();
            Element author          = post.select("div.author > a").first();
            Element views           = post.select("div.pageviews").first();
            Element comments        = post.select("div.comments > a span.all").first();
            Element newComments     = post.select("div.comments > a span.new").first();
            Element rating          = post.select("div.mark").first();
            Element favorites       = post.select("div.favs_count").first();
            /*Element score           = post.select("div.mark > span.score").first();
            Element flag            = post.select(".flag").first();
            Element favoriteMark    = post.select("div.favorite").first();*/

            postData.setId(postId);
            //postsData.setVisited(visitedPostsIds.indexOf(postId) != -1);
            postData.setImage(postImage != null ? postImage.absUrl("src") : "");
            if (postTitle != null){
                postData.setTitle(postTitle.text());
                postData.setUrl(postTitle.attr("abs:href"));
            }
            if (habracut != null){
                habracut.select("img").remove();
                postData.setHabracut(habracut.html());
                String[] cutWords = habracut.text().split("\\s+");
                int wordsNumber = cutWords.length < 30 ? cutWords.length : 30;
                String[] shortHabracutWords = new String[wordsNumber];
                for (int i = 0; i < wordsNumber; i++){
                    shortHabracutWords[i] = cutWords[i];
                }
                postData.setHabracut(Utils.arrayToString(shortHabracutWords, " "));
            }
            postData.setHubs(hubs != null ? hubs.text() : "");
            postData.setViews(views != null ? Integer.parseInt(views.text()) : 0);
            postData.setFavoritesCount(favorites != null && !favorites.text().equals("") ? Integer.parseInt(favorites.text().trim()) : 0);
            postData.setDate(System.currentTimeMillis());
            if (originalAuthor != null){
                postData.setOriginalAuthor(originalAuthor.text());
                postData.setOriginalUrl(originalAuthor.attr("abs:href"));
            }
            if (author != null){
                postData.setAuthor(author.text());
                postData.setAuthorUrl(author.attr("abs:href"));
            }
            if (comments != null)   postData.setCommentsCount(!comments.text().equals("Комментировать") ? Integer.parseInt(comments.text()) : 0);
            postData.setNewCommentsCount(newComments != null ? Integer.parseInt(newComments.text().replace("+", "")) : -1);
            if (rating != null){
                if (!rating.text().equals("—")){
                    int parsedRating = Integer.parseInt(rating.text().replaceAll("\\D", ""));
                    if (rating.text().startsWith("—")){
                        parsedRating = -parsedRating;
                    }
                    postData.setRating(parsedRating);
                }
            }

            /*if (flag != null)
            {
                if (flag.hasClass("flag_translation")){
                    postsData.setFlag(PostListItemData.FLAG_TRANSLATION);
                }else if (flag.hasClass("flag_sandbox")){
                    postsData.setFlag(PostListItemData.FLAG_SANDBOX);
                }else if (flag.hasClass("flag_solution")){
                    postsData.setFlag(PostListItemData.FLAG_SOLUTION);
                }else if (flag.hasClass("flag_recovery")){
                    postsData.setFlag(PostListItemData.FLAG_RECOVERY);
                }else if (flag.hasClass("flag_tutorial")){
                    postsData.setFlag(PostListItemData.FLAG_TUTORIAL);
                }
            }*/

            /*if (favoriteMark != null){
                postsData.setFavorite(favoriteMark.select("a").first().hasClass("remove") ? true : false);
            }*/

            response.add(postData);
        }

        return response;
    }

    public static PostContentResponse getPost(int id) throws IOException {
        PostContentResponse response = new PostContentResponse();

        Logger.v("Load post: " + id);

        PostContentData postData = new PostContentData();

        String url = BASE_URL + "post/" + id;

        long startTime = System.currentTimeMillis();

        Document document = Jsoup.connect(url)
                .get();

        Element post = document.select("div.post").first();

        if (post != null){
            Element title           = post.select("span.post_title").first();
            Element imagePreview    = post.select("div.content img").first();
            Element hubs            = post.select("div.hubs").first();
            Element content         = post.select("div.content").first();
            //Element date            = post.select("div.published").first();
            Element author          = post.select("div.author > a").first();
            Element originalAuthor  = post.select("div.original-author > a").first();
            Element commentsCount   = document.select("span#comments_count").first();
            //Element voting          = post.select("div.voting").first();
            Element rating          = post.select("div.mark").first();
            //Element score           = rating.select("span.score").first();
            //Element flag            = post.select(".flag").first();
            //Element ts              = document.select("#comments_form input[name=ts]").first();
            //Element favoriteMark    = post.select("div.favorite").first();
            Element favoriteCount   = post.select("div.favs_count").first();

            int postId = Integer.parseInt(post != null ? post.attr("id").replace("post_", "") : "");

            postData.setId(postId);
            postData.setTitle(title != null ? title.text() : "");
            postData.setImage(imagePreview != null ? imagePreview.absUrl("src") : "");
            postData.setHubs(hubs != null ? hubs.text() : "");
            postData.setContent(content != null ? content.html() : "");
            // TODO: add date parsing
            postData.setDate(System.currentTimeMillis());
            if (author != null){
                postData.setAuthor(author.text());
                postData.setAuthorUrl(author.attr("abs:href"));
            }
            if (originalAuthor != null){
                postData.setOriginalAuthor(originalAuthor.text());
                postData.setOriginalUrl(originalAuthor.attr("abs:href"));
            }
            postData.setCommentsCount(commentsCount != null ? Integer.parseInt(commentsCount.text()) : 0);
            if (rating != null){
                if (!rating.text().equals("—")){
                    int parsedRating = Integer.parseInt(rating.text().replaceAll("\\D", ""));
                    if (rating.text().startsWith("—")){
                        parsedRating = -parsedRating;
                    }
                    postData.setRating(parsedRating);
                }
            }
            postData.setFavoritesCount(
                    (favoriteCount != null && !android.text.TextUtils.isEmpty(favoriteCount.text()))
                            ? Integer.parseInt(favoriteCount.text()) : 0
            );

            // Parse comments
            Elements comments = document.select("div.comment_item");

            for (Element comment : comments){
                CommentListItemData commentData = new CommentListItemData();

                int commentId = Integer.parseInt(comment.select("div.comment_item").first().attr("id").replace("comment_", ""));
                commentData.setCommentId(commentId);

                Element parentId = comment.select("span.parent_id").first();
                Element avatar = comment.select("a.avatar > img").first();
                Element name = comment.select("a.username").first();
                //Element commentDate = comment.select("time").first();
                Element message = comment.select("div.message").first();
                Element linkToComment = comment.select("a.link_to_comment").first();
                Element commentRating = comment.select("span.score").first();
                //Element info = comment.select("div.info").first();
                //Element favorite = comment.select("a.favorite").first();
                //Element voting = comment.select("div.voting").first();

                commentData.setParentCommentId(parentId != null ? Integer.parseInt(parentId.attr("data-parent_id")) : 0);
                commentData.setParentPostId(postId);
                if (name != null){
                    commentData.setAuthor(name.text());
                    commentData.setAuthorUrl(name.attr("abs:href"));
                }
                commentData.setAvatarUrl(avatar != null ? avatar.absUrl("src") : "");
                commentData.setCommentUrl(linkToComment != null ? linkToComment.attr("abs:href") : "");
                if (commentRating != null){
                    int parsedRating = Integer.parseInt(commentRating.text().replaceAll("\\D", ""));
                    // Check if string contains negative mark.
                    // CAUTION: special char with hashcode 8211 used instead of simple minus ("-") char with code 45
                    if (commentRating.text().startsWith("–")){
                        parsedRating = -parsedRating;
                    }
                    commentData.setRating(parsedRating);
                }
                commentData.setContent(message != null ? message.html() : "");
                //commentData.setDate(System.currentTimeMillis());
                postData.getComments().add(commentData);
            }
        }

        response.setPost(postData);
        response.setSuccess(true);

        Logger.v("Load time: " + (System.currentTimeMillis() - startTime) + "ms");

        return response;
    }
}
