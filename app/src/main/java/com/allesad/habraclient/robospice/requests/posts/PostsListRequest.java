package com.allesad.habraclient.robospice.requests.posts;

import com.allesad.habraclient.database.DBHelper;
import com.allesad.habraclient.database.dao.posts.PostDao;
import com.allesad.habraclient.database.models.posts.Comment;
import com.allesad.habraclient.database.models.posts.Post;
import com.allesad.habraclient.helpers.RequestHelper;
import com.allesad.habraclient.interfaces.models.posts.IPost;
import com.allesad.habraclient.model.posts.PostListItemData;
import com.allesad.habraclient.robospice.response.posts.PostsListResponse;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allesad on 25.03.2014.
 */
public class PostsListRequest extends SpiceRequest<PostsListResponse> {

    private String mUrl;
    private int mPage;

    public PostsListRequest(String url, int page) {
        super(PostsListResponse.class);

        mUrl = url;
        mPage = page;
    }

    @Override
    public PostsListResponse loadDataFromNetwork() throws Exception {
        String readyUrl = mUrl.replace("%page%", String.valueOf(mPage));
        PostsListResponse response;

        try {
            response = RequestHelper.getPosts(readyUrl);

            // Save posts to cache
            // savePostsToCache(response);
        }catch (IOException ex){
            return new PostsListResponse();
        }

        return response;
    }

    // TODO: rework for using cache dao for posts
    private void savePostsToCache(PostsListResponse posts){
        try {
            PostDao dao = DBHelper.get().getPostDao();
            Post post;

            for (Post postItem : posts) {
                Post cachedPost = dao.queryForId(postItem.getPost_id());
                if (cachedPost != null){
                    post = Post.copy(cachedPost);
                    post.commentsCount = postItem.getPost_commentsCount();
                    post.newCommentsCount = postItem.getPost_newCommentsCount();
                }else{
                    post = Post.copy(postItem);
                }
                dao.createOrUpdate(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
