package com.allesad.habraclient.fragments.posts;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.posts.PostCommentsAdapter;
import com.allesad.habraclient.adapters.posts.PostCommentsAdapter2;
import com.allesad.habraclient.events.PostEvent;
import com.allesad.habraclient.fragments.BaseSpicedFragment;
import com.allesad.habraclient.model.posts.CommentListItemData;
import com.allesad.habraclient.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Allesad on 13.04.2014.
 */
public class PostCommentsFragment extends BaseSpicedFragment implements AdapterView.OnItemClickListener
{
    //=============================================================
    // Variables
    //=============================================================

    private ProgressBar mProgressBar;
    //private ListView mCommentsList;
    private RecyclerView mCommentsList;
    //private PostCommentsAdapter mAdapter;
    private PostCommentsAdapter2 mAdapter;

    private List<CommentListItemData> mComments;

    //=============================================================
    // Static initializer
    //=============================================================

    public static PostCommentsFragment newInstance() {
        return new PostCommentsFragment();
    }
    public PostCommentsFragment() {
        // Required empty public constructor
    }

    //=============================================================
    // Fragment lifecycle
    //=============================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        //mCommentsList = (ListView) view.findViewById(R.id.commentsView_list);
        mCommentsList = (RecyclerView) view.findViewById(android.R.id.list);
        mCommentsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentsList.setItemAnimator(new DefaultItemAnimator());
        /*TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
        mCommentsList.setEmptyView(emptyView);

        mCommentsList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpCommentsList();
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);

        super.onPause();
    }
    
    //=============================================================
    // AdapterView.OnItemClickListener
    //=============================================================

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Logger.v("Comment clicked. ID: " + id);
        /*for (CommentListItemData comment : mComments){
            if (comment.getCommentId() == id){
                comment.setShowFullContent(comment.isShowFullContent() ? false : true);
            }else{
                comment.setShowFullContent(false);
            }
        }
        mAdapter.notifyDataSetChanged();*/
    }

    //=============================================================
    // Public methods
    //=============================================================

    public void onEvent(PostEvent event){
        if (event.getPost() != null){
            mComments.clear();
            mComments.addAll(event.getPost().getComments());
            mAdapter.notifyDataSetChanged();

            mProgressBar.setVisibility(View.GONE);
            mCommentsList.setVisibility(View.VISIBLE);
        }
    }

    //=============================================================
    // Private methods
    //=============================================================

    private void setUpCommentsList(){
        if (mComments == null){
            mComments = new ArrayList<CommentListItemData>();
        }
        if (mAdapter == null){
            mAdapter = new PostCommentsAdapter2(getActivity(), mComments);
        }
        mCommentsList.setAdapter(mAdapter);
        //mCommentsList.setOnItemClickListener(this);
    }
}
