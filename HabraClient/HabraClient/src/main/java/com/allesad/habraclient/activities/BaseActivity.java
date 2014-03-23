package com.allesad.habraclient.activities;

import android.support.v4.app.FragmentActivity;

import com.allesad.habraclient.robospice.BaseSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Allesad on 23.03.2014.
 */
public class BaseActivity extends FragmentActivity {

    private SpiceManager mSpiceManager = new SpiceManager(BaseSpiceService.class);

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
}
