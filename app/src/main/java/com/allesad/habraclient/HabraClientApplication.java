package com.allesad.habraclient;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.allesad.habraclient.utils.ImageDownloaderExtended;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Allesad on 05.04.2014.
 */
public class HabraClientApplication extends Application {

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();

        int stubImageResource = R.drawable.ic_no_image;
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(stubImageResource)
                .showImageForEmptyUri(stubImageResource)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(imageOptions)
                .diskCacheSize(40 * 1024 * 1024)
                .imageDownloader(new ImageDownloaderExtended(this))
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    public static Context getAppContext(){
        return mAppContext;
    }
}
