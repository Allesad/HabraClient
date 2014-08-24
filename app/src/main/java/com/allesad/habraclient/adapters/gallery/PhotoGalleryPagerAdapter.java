package com.allesad.habraclient.adapters.gallery;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allesad.habraclient.R;
import com.allesad.habraclient.components.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Allesad on 12.04.2014.
 */
public class PhotoGalleryPagerAdapter extends PagerAdapter {

    private LayoutInflater mInflater;
    private List<String> mImagePaths;

    public PhotoGalleryPagerAdapter(Context context, List<String> imagePaths){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return mImagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imageView;

        imageView = (TouchImageView) mInflater.inflate(R.layout.view_gallery_image, container, false);

        ImageLoader.getInstance().displayImage(mImagePaths.get(position), imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((TouchImageView) object);
    }
}
