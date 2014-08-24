package com.allesad.habraclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.posts.PostContentPagerAdapter;
import com.allesad.habraclient.components.ViewPagerExtended;
import com.allesad.habraclient.components.WebViewExtended;
import com.allesad.habraclient.fragments.posts.PostContentFragment;
import com.allesad.habraclient.helpers.IntentHelper;
import com.allesad.habraclient.interfaces.IPostDataProvider;
import com.allesad.habraclient.interfaces.ISpiceManagerProvider;
import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.robospice.requests.posts.PostContentRequest;
import com.allesad.habraclient.robospice.response.posts.PostContentResponse;
import com.allesad.habraclient.utils.ArgumentConstants;
import com.allesad.habraclient.utils.BroadcastConstants;
import com.allesad.habraclient.utils.DialogUtil;
import com.allesad.habraclient.utils.Logger;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

/**
 * Created by Allesad on 09.04.2014.
 */
public class PostContentActivity extends BaseActivity implements ISpiceManagerProvider,
        View.OnTouchListener, WebViewExtended.OnScrollListener, IPostDataProvider {

    private final static String KEY_FULLSCREEN = "fullscreen";
    private final static String KEY_POST_DATA = "postData";

    private View mDecorView;

    private PostContentData mPost;

    private boolean mFullscreen;
    private float mLastX;
    private float mLastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.view_content);

        getWindow().setBackgroundDrawableResource(R.drawable.content_bg_light);
        getWindow().setExitTransition(new Explode());

        mDecorView = getWindow().getDecorView();

        mFullscreen = true;
        if (savedInstanceState != null){
            mFullscreen = savedInstanceState.getBoolean(KEY_FULLSCREEN);
            mPost = (PostContentData) savedInstanceState.getSerializable(KEY_POST_DATA);
        }

        if (mFullscreen){
            hideSystemUI();
        }

        int postId = getIntent().getExtras().getInt(ArgumentConstants.POST_ID);
        if (mPost == null){
            getSpiceManager().execute(new PostContentRequest(postId), new PostContentRequestListener());
        }else{
            triggerContentUpdate();
        }

        PostContentPagerAdapter adapter = new PostContentPagerAdapter(getSupportFragmentManager());
        ViewPagerExtended pager = (ViewPagerExtended) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_FULLSCREEN, mFullscreen);
        outState.putSerializable(KEY_POST_DATA, mPost);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFullscreen){
            hideSystemUI();
        }else{
            showSystemUI();
        }
    }

    @Override
    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                return false;
            case MotionEvent.ACTION_UP:
                final float dx = Math.abs(event.getX() - mLastX);
                final float dy = Math.abs(event.getY() - mLastY);

                //Logger.v("dx: " + dx + ", dy: " + dy);

                if (dx <= 3 && dy <= 3){
                    // Detect tap on image.
                    WebViewExtended.HitTestResult hr = null;
                    if (view instanceof WebViewExtended)
                        hr = ((WebViewExtended) view).getHitTestResult();

                    if (hr != null){
                        // If tapped object is image show it in the popup
                        if (hr.getType() == WebView.HitTestResult.IMAGE_TYPE){
                            String url = hr.getExtra();
                            if (url != null && url.length() > 0){
                                ArrayList<String> imagePaths = (ArrayList<String>) ((WebViewExtended) view).getImagePaths();
                                String selectedImage = url.replace("habrastorage.org", "beta.hstor.org");
                                IntentHelper.toPostGalleryScreen(PostContentActivity.this, imagePaths, selectedImage);
                                return true;
                            }
                        }
                        //Logger.v("getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                    }

                    if (mFullscreen){
                        showSystemUI();
                    }else{
                        hideSystemUI();
                    }
                }
                return false;
        }
        return false;
    }

    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
        if ((t - oldt) < 20){
            return;
        }

        if (t > oldt && !mFullscreen){
            hideSystemUI();
        }
    }

    @Override
    public PostContentData getPost() {
        return mPost;
    }

    private void hideSystemUI(){
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        mFullscreen = true;
    }

    private void showSystemUI(){
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mFullscreen = false;
    }

    private void triggerContentUpdate(){
        // Trigger update in view pager fragments
        HabraClientApplication.localBroadcastManager.sendBroadcast(new Intent(BroadcastConstants.POST_DATA_UPDATE));
    }

    private class PostContentRequestListener implements RequestListener<PostContentResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            DialogUtil.showAlertDialog(PostContentActivity.this, spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(PostContentResponse response) {
            if (response.isSuccess()){
                mPost = response.getPost();

                triggerContentUpdate();
            }else{
                DialogUtil.showAlertDialog(PostContentActivity.this, response.getMessage());
            }
        }
    }
}
