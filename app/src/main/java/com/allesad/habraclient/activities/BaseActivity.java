package com.allesad.habraclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.Window;

import com.allesad.habraclient.robospice.BaseSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Allesad on 23.03.2014.
 */
public class BaseActivity extends FragmentActivity {

    protected SpiceManager mSpiceManager = new SpiceManager(BaseSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        Transition transition = new Slide();
        getWindow().setExitTransition(transition);
        getWindow().setEnterTransition(transition);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSpiceManager.start(this);
    }

    @Override
    protected void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
