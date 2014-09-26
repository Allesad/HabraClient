package com.allesad.habraclient.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.posts.PostContentPagerAdapter;
import com.allesad.habraclient.components.ViewPagerExtended;
import com.allesad.habraclient.components.views.WebViewExtended;
import com.allesad.habraclient.events.PostEvent;
import com.allesad.habraclient.helpers.IntentHelper;
import com.allesad.habraclient.interfaces.ISpiceManagerProvider;
import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.robospice.requests.posts.PostContentRequest;
import com.allesad.habraclient.robospice.response.posts.PostContentResponse;
import com.allesad.habraclient.utils.ArgumentConstants;
import com.allesad.habraclient.utils.DialogUtil;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Allesad on 09.04.2014.
 */
public class PostContentActivity extends BaseActivity implements ISpiceManagerProvider,
        View.OnTouchListener, WebViewExtended.OnScrollListener
{
    //=============================================================
    // Constants
    //=============================================================
    private final static String KEY_FULLSCREEN = "fullscreen";
    private final static String KEY_POST_DATA = "postData";

    //=============================================================
    // Variables
    //=============================================================

    private View mDecorView;

    private PostContentData mPost;

    private boolean mFullscreen;
    private float mLastX;
    private float mLastY;
    
    //=============================================================
    // Activity lifecycle
    //=============================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        setContentView(R.layout.view_content);

        getWindow().setBackgroundDrawableResource(R.drawable.content_bg_light);

        mDecorView = getWindow().getDecorView();

        mFullscreen = true;
        if (savedInstanceState != null){
            mFullscreen = savedInstanceState.getBoolean(KEY_FULLSCREEN);
            mPost = (PostContentData) savedInstanceState.getSerializable(KEY_POST_DATA);
        }

        if (mFullscreen){
            hideSystemUI();
        }

        int postId = (int) getIntent().getExtras().getLong(ArgumentConstants.POST_ID);
        if (mPost == null){
            getSpiceManager().execute(new PostContentRequest(postId), new PostContentRequestListener());
        }else{
            triggerContentUpdate();
        }

        PostContentPagerAdapter adapter = new PostContentPagerAdapter(getSupportFragmentManager(), mPost);
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
    protected void onResume() {
        super.onResume();

        if (mFullscreen){
            hideSystemUI();
        }else{
            showSystemUI();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
    
    //=============================================================
    // ISpiceManagerProvider
    //=============================================================

    @Override
    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }
    
    //=============================================================
    // View.OnTouchListener
    //=============================================================

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
                                String selectedImage = url.replace("//habrastorage.org", "http://beta.hstor.org");
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
    
    //=============================================================
    // WebViewExtended.OnScrollListener
    //=============================================================

    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
        if ((t - oldt) < 20){
            return;
        }

        if (t > oldt && !mFullscreen){
            hideSystemUI();
        }
    }
    
    //=============================================================
    // Private methods
    //=============================================================

    private void hideSystemUI(){
        /*mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        mFullscreen = true;*/
    }

    private void showSystemUI(){
        /*mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mFullscreen = false;*/
    }

    private void triggerContentUpdate(){
        // Trigger update in view pager fragments
        EventBus.getDefault().post(new PostEvent(mPost));
    }
    
    //=============================================================
    // PostContentRequestListener
    //=============================================================

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
