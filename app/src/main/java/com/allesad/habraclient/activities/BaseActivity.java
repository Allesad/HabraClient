package com.allesad.habraclient.activities;

import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.allesad.habraclient.robospice.BaseSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Allesad on 23.03.2014.
 */
public class BaseActivity extends FragmentActivity {

    protected SpiceManager mSpiceManager = new SpiceManager(BaseSpiceService.class);

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
