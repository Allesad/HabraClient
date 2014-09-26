package com.allesad.habraclient.utils;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sakhno on 8/24/2014.
 */
public class ImageDownloaderExtended extends BaseImageDownloader {

    /** {@value} */
    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000; // milliseconds
    /** {@value} */
    public static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000; // milliseconds

    /*private int connectTimeout;
    private int readTimeout;

    private SSLSocketFactory sf;*/

    public ImageDownloaderExtended(Context context) {
        super(context);
    }

    public ImageDownloaderExtended(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    /*@Override
    public InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) imageUri.toURL().openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);

        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection)conn).setSSLSocketFactory(sf);
        }

        return new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
    }*/

    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        if (imageUri.startsWith("//")){
            imageUri = "http://" + imageUri.substring(2);
        }
        return super.getStreamFromNetwork(imageUri, extra);
    }
}
