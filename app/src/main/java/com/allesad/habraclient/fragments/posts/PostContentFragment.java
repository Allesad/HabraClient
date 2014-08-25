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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.activities.MainActivity;
import com.allesad.habraclient.components.WebViewExtended;
import com.allesad.habraclient.events.PostEvent;
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
import com.melnykov.fab.FloatingActionButton;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import de.greenrobot.event.EventBus;

/**
 * Created by Allesad on 09.04.2014.
 *
 */
public class PostContentFragment extends BaseSpicedFragment
{
    //=============================================================
    // Variables
    //=============================================================

    private WebViewExtended mContent;
    private ProgressBar mProgress;

    private WebViewExtended.OnScrollListener mContentScrollListener;

    //=============================================================
    // Static initializer
    //=============================================================

    public static PostContentFragment newInstance() {
        return new PostContentFragment();
    }
    public PostContentFragment() {
        // Required empty public constructor
    }

    //=============================================================
    // Fragment lifecycle
    //=============================================================

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mContentScrollListener = (WebViewExtended.OnScrollListener) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement OnScrollListener interface!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContentScrollListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_post_content_real, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContent = (WebViewExtended) view.findViewById(R.id.postView_content);
        mProgress = (ProgressBar) view.findViewById(R.id.postView_progress);
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.button_floating_action);
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = fab.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }

        mContent.setOnTouchListener((View.OnTouchListener) getActivity());
        final int finalMarginBottom = marginBottom;
        mContent.setScrollListener(new WebViewExtended.OnScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                mContentScrollListener.onScroll(l, t, oldl, oldt);
                if (t > oldt){

                    fab.animate().setInterpolator(interpolator)
                            .setDuration(200)
                            .translationY(fab.getHeight() + finalMarginBottom);
                }else if (t < oldt) {
                    fab.animate().setInterpolator(interpolator)
                            .setDuration(200)
                            .translationY(0);
                }
            }
        });


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
    // Public methods
    //=============================================================

    public void onEvent(PostEvent event){
        if (event.getPost() != null){
            mContent.loadPost(event.getPost());
            mProgress.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        }
    }

    //=============================================================
    // Private methods
    //=============================================================
}
