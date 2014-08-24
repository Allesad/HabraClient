package com.allesad.habraclient.robospice.requests;

import com.allesad.habraclient.helpers.RequestHelper;
import com.allesad.habraclient.model.posts.PostListItemData;
import com.allesad.habraclient.robospice.response.posts.PostsListResponse;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;

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
        }catch (IOException ex){
            return null;
        }

        // TODO: replace with actual request
        /*for (int i = 0; i < 10; i++){
            PostListItemData post = new PostListItemData();
            post.setTitle("Title " + i);
            post.setAuthor("Author " + i);
            post.setHubs("Android, Mobile Development");
            if ((i % 2) == 0){
                post.setImage("http://habrastorage.org/getpro/habr/post_images/4f1/7ab/88b/4f17ab88b556d72cf5901f4f873d89e7.jpg");
            }
            response.add(post);
        }*/

        return response;
    }
}
