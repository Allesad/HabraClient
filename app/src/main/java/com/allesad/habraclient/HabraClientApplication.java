package com.allesad.habraclient;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Allesad on 05.04.2014.
 */
public class HabraClientApplication extends Application {

    public static LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        int stubImageResource = R.drawable.ic_no_image;
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(stubImageResource)
                .showImageForEmptyUri(stubImageResource)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(imageOptions)
                .discCacheSize(40 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
