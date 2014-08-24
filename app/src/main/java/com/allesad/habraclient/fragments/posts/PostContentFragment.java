package com.allesad.habraclient.fragments.posts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.activities.MainActivity;
import com.allesad.habraclient.components.WebViewExtended;
import com.allesad.habraclient.fragments.BaseSpicedFragment;
import com.allesad.habraclient.interfaces.IPostDataProvider;
import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.robospice.requests.posts.PostContentRequest;
import com.allesad.habraclient.robospice.response.posts.PostContentResponse;
import com.allesad.habraclient.utils.ArgumentConstants;
import com.allesad.habraclient.utils.BroadcastConstants;
import com.allesad.habraclient.utils.DialogUtil;
import com.allesad.habraclient.utils.KeysContstants;
import com.allesad.habraclient.utils.Logger;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Allesad on 09.04.2014.
 *
 */
public class PostContentFragment extends BaseSpicedFragment {

    private WebViewExtended mContent;
    private ProgressBar mProgress;

    private WebViewExtended.OnScrollListener mContentScrollListener;
    private IPostDataProvider mPostDataProvider;

    private boolean mReceiverRegistered;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action == null){
                return;
            }else if (action.equals(BroadcastConstants.POST_DATA_UPDATE)){
                showPostContent();
            }
        }
    };

    public static PostContentFragment newInstance() {
        return new PostContentFragment();
    }
    public PostContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mContentScrollListener = (WebViewExtended.OnScrollListener) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement OnScrollListener interface!");
        }

        try {
            mPostDataProvider = (IPostDataProvider) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement IPostDataProvider interface!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContentScrollListener = null;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_post_content_real, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContent = (WebViewExtended) view.findViewById(R.id.postView_content);
        mProgress = (ProgressBar) view.findViewById(R.id.postView_progress);

        mContent.setOnTouchListener((View.OnTouchListener) getActivity());
        mContent.setScrollListener(mContentScrollListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        registerUpdateReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterUpdateReceiver();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KeysContstants.KEY_RECEIVER_REGISTERED_FLAG, mReceiverRegistered);
    }

    private void showPostContent(){
        if (mPostDataProvider.getPost() != null){
            mContent.loadPost(mPostDataProvider.getPost());
            mProgress.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        }
    }

    private void registerUpdateReceiver(){
        if (!mReceiverRegistered){
            HabraClientApplication.localBroadcastManager.registerReceiver(mReceiver, new IntentFilter(BroadcastConstants.POST_DATA_UPDATE));
            mReceiverRegistered = true;
        }
    }

    private void unregisterUpdateReceiver(){
        HabraClientApplication.localBroadcastManager.unregisterReceiver(mReceiver);
        mReceiverRegistered = false;
    }
}
