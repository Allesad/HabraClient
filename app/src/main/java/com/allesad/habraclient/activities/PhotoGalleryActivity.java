package com.allesad.habraclient.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.allesad.habraclient.R;
import com.allesad.habraclient.adapters.gallery.PhotoGalleryPagerAdapter;
import com.allesad.habraclient.components.ViewPagerExtended;
import com.allesad.habraclient.utils.ArgumentConstants;

public class PhotoGalleryActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private PhotoGalleryPagerAdapter mAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPagerExtended mViewPager;

    private int mCurrentImagePosition;
    private int mTotalImagesCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_photo_gallery);

        List<String> imagePaths = getIntent().getStringArrayListExtra(ArgumentConstants.IMAGE_PATHS);
        String selectedImage = getIntent().getStringExtra(ArgumentConstants.SELECTED_IMAGE);
        mCurrentImagePosition = imagePaths.indexOf(selectedImage);
        mTotalImagesCounter = imagePaths.size();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mAdapter = new PhotoGalleryPagerAdapter(this, imagePaths);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPagerExtended) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(mCurrentImagePosition);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        setUpTitle();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.photo_gallery, menu);

        return false;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        mCurrentImagePosition = position;
        setUpTitle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void setUpTitle(){
        String title = (mCurrentImagePosition + 1) + " " + getString(R.string.of) + " " + mTotalImagesCounter;
        setTitle(title);
    }
}
