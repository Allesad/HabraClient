package com.allesad.habraclient.utils;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sakhno on 8/24/2014.
 */
public class ImageDownloaderExtended extends BaseImageDownloader {

    public ImageDownloaderExtended(Context context) {
        super(context);
    }

    public ImageDownloaderExtended(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        if (imageUri.startsWith("//")){
            imageUri = "http://" + imageUri.substring(2);
        }
        return super.getStreamFromNetwork(imageUri, extra);
    }
}
