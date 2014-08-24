package com.allesad.habraclient.fragments.posts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.posts.PostCommentsAdapter;
import com.allesad.habraclient.fragments.BaseSpicedFragment;
import com.allesad.habraclient.interfaces.IPostDataProvider;
import com.allesad.habraclient.model.posts.CommentListItemData;
import com.allesad.habraclient.utils.BroadcastConstants;
import com.allesad.habraclient.utils.KeysContstants;
import com.allesad.habraclient.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allesad on 13.04.2014.
 */
public class PostCommentsFragment extends BaseSpicedFragment implements AdapterView.OnItemClickListener {

    private ProgressBar mProgressBar;
    private ListView mCommentsList;
    private PostCommentsAdapter mAdapter;

    private List<CommentListItemData> mComments;
    private IPostDataProvider mPostDataProvider;

    private boolean mReceiverRegistered;

    public static PostCommentsFragment newInstance() {
        return new PostCommentsFragment();
    }
    public PostCommentsFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastConstants.POST_DATA_UPDATE)){
                showComments();
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mPostDataProvider = (IPostDataProvider) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement IPostDataProvider interface!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mPostDataProvider = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mReceiverRegistered = savedInstanceState.getBoolean(KeysContstants.KEY_RECEIVER_REGISTERED_FLAG);
        }
        registerUpdateReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mCommentsList = (ListView) view.findViewById(R.id.commentsView_list);
        TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
        mCommentsList.setEmptyView(emptyView);

        mCommentsList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpCommentsList();
    }

    @Override
    public void onStart() {
        super.onStart();

        registerUpdateReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();

        HabraClientApplication.localBroadcastManager.unregisterReceiver(mReceiver);
        mReceiverRegistered = false;
    }

    private void setUpCommentsList(){
        if (mComments == null){
            mComments = new ArrayList<CommentListItemData>();
        }
        if (mAdapter == null){
            mAdapter = new PostCommentsAdapter(getActivity(), mComments);
        }
        mCommentsList.setAdapter(mAdapter);
        mCommentsList.setOnItemClickListener(this);
    }

    private void showComments(){
        if (mPostDataProvider.getPost() != null){
            mComments.clear();
            mComments.addAll(mPostDataProvider.getPost().getComments());
            mAdapter.notifyDataSetChanged();

            mProgressBar.setVisibility(View.GONE);
            mCommentsList.setVisibility(View.VISIBLE);
        }
    }

    private void registerUpdateReceiver(){
        if (!mReceiverRegistered){
            HabraClientApplication.localBroadcastManager.registerReceiver(mReceiver, new IntentFilter(BroadcastConstants.POST_DATA_UPDATE));
            mReceiverRegistered = true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Logger.v("Comment clicked. ID: " + id);
        for (CommentListItemData comment : mComments){
            if (comment.getCommentId() == id){
                comment.setShowFullContent(comment.isShowFullContent() ? false : true);
            }else{
                comment.setShowFullContent(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
