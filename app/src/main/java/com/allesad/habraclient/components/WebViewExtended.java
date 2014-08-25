package com.allesad.habraclient.components;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.allesad.habraclient.R;
import com.allesad.habraclient.activities.PostContentActivity;
import com.allesad.habraclient.model.posts.CommentListItemData;
import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.utils.Logger;
import com.allesad.habraclient.utils.Utils;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allesad on 09.04.2014.
 */
public class WebViewExtended extends WebView {

    private Context mContext;
    private List<String> mImagePaths;
    private OnScrollListener mScrollListener;

    public WebViewExtended(Context context) {
        super(context);

        mContext = context;
        init();
    }

    public WebViewExtended(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init();
    }

    public WebViewExtended(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        init();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mScrollListener != null){
            mScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    public void setScrollListener(OnScrollListener listener){
        mScrollListener = listener;
    }

    public void loadPost(PostContentData post){
        String content = post.getContent();
        content = parseDocument(content);
        content = addTitleInfo(post, content);
        content = applyWrapperInfo(content);

        super.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "UTF-8", null);
    }

    public void loadComment(CommentListItemData comment){
        String content = comment.getContent();
        content = parseDocument(content);
        content = applyWrapperInfo(content);

        super.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "UTF-8", null);
    }

    public List<String> getImagePaths(){
        return mImagePaths;
    }

    private void init(){
        mImagePaths = new ArrayList<String>();

        getSettings().setSupportZoom(false);
        getSettings().setBuiltInZoomControls(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getSettings().setDisplayZoomControls(false);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDefaultFontSize(18);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }*/
        //getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        //getSettings().setDomStorageEnabled(true);

        // Set cache
        String appCachePath = mContext.getApplicationContext().getCacheDir().getAbsolutePath();
        getSettings().setAppCachePath(appCachePath);
        getSettings().setAppCacheEnabled(true);

        setWebViewClient(new ContentWebClient());
    }

    private String parseDocument(String data){
        Document document = Jsoup.parse(data);

        // Extract images
        Elements images = document.select("img");

        for (Element image : images){
            image.setBaseUri("http://beta.hstor.org/");
            String url = image.absUrl("src");
            url = url.replace("http://habrastorage.org", "http://beta.hstor.org");
            Logger.v("Image URL: " + url);
            image.attr("src", url);
            if (!TextUtils.isEmpty(url)){
                mImagePaths.add(url);
            }
        }
        Logger.v("Content: " + document.body().html());

        // Find all youtube frames on the page and replace them with video preview
        Elements iframes = document.select("iframe");

        for (Element iframe : iframes){
            if (iframe.hasAttr("src") && iframe.attr("src").contains("www.youtube.com/embed")){
                String videoId = iframe.attr("src").substring(iframe.attr("src").lastIndexOf("/") + 1);
                videoId = videoId.substring(0, videoId.indexOf("?"));
                String videoUrl = "http://www.youtube.com/watch?v=" + videoId;
                Logger.v("Video url is: " + videoUrl);
                iframe.wrap("<a href='" + videoUrl + "'><img src='http://img.youtube.com/vi/" + videoId + "/0.jpg'></img></a>");
                iframe.remove();
            }
        }

        // Find other types of video elements and replace them with button to open video in browser
        Elements objects = document.select("object");
        for (Element object : objects){
            String source = "";
            Element embed = object.select("embed").first();
            if (embed != null){
                source = embed.hasAttr("src") ? embed.attr("src") : "";
            }
            if (TextUtils.isEmpty(source)){
                Elements params = object.select("param");
                for (Element param : params){
                    if (param.attr("name").equals("video")){
                        source = param.attr("value");
                        break;
                    }
                }
            }
            object.wrap("<center><a href=\"" + source + "\" class=\"btnVideo\">" + getContext().getString(R.string.open_video) + "</a></center>");
            object.remove();
        }

        // Find all links and cut their text to fit the screen
        Elements links = document.select("a");
        for (Element link : links){
            String linkText = link.text();
            if (!linkText.contains(" ") && linkText.length() > 35){
                linkText = linkText.substring(0, 35) + "...";
                link.text(linkText);
            }
        }

        //Logger.v("Page HTML is: " + data);
        return document.body().html();
    }

    private String addTitleInfo(PostContentData post, String content){
        content = "<div class=\"published\">" + post.getDateFormatted() + ", " + post.getAuthor() + "</div>\n"
                + "<h1 class=\"title\">" + post.getTitle() + "</h1>\n"
                + "<div class=\"hubs\">" + post.getHubs() + "</div>\n"
                + content;

        return content;
    }

    private String applyWrapperInfo(String content){
        content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\">\n" +
                "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/styles.css\"/>" +
                "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/syntaxHighlight.css\"/>" +
                "\t</head>\n" +
                "\t<body>\n" +
                content +
                "\t\t<script src=\"file:///android_asset/highlight.js\"></script>" +
                "\t\t<script type=\"text/javascript\">hljs.initHighlightingOnLoad();</script>" +
                "\t</body>\n" +
                "</html>";

        /*
                "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/blackTheme.css\"/>" +*/

        return content;
    }

    private class ContentWebClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            if (url.endsWith("png") || url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("gif")){
                try{
                    File cacheDir   = StorageUtils.getCacheDirectory(mContext);
                    //url = url.replace("file://", "http://");
                    String fileName = String.valueOf(url.hashCode());
                    File imageFile  = new File(cacheDir, fileName);
                    if (!imageFile.exists()){
                        cacheDir    = StorageUtils.getIndividualCacheDirectory(mContext);
                        imageFile = new File(cacheDir, fileName);
                    }
                    if (!imageFile.exists()){
                        return super.shouldInterceptRequest(view, url);
                    }
                    InputStream is  = new FileInputStream(imageFile);
                    Logger.v("Load image from cache. Is: " + is.toString());
                    return new WebResourceResponse("image/*", "base64", is);

                }catch (Exception ex){
                    Logger.v("Load image as normal. Error: " + ex.getMessage());
                    return super.shouldInterceptRequest(view, url);
                }
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Boolean hasInternet = Utils.hasInternetConnection(mContext);

            Intent intent = null;

            // Check if url is not complete habrahabr link
            if (url.startsWith("/login/")   ||
                    url.startsWith("/post/")    ||
                    url.startsWith("/hub/")     ||
                    url.startsWith("/qa/")      ||
                    url.startsWith("/events/")  ||
                    url.startsWith("/company/") ||
                    url.startsWith("/users/"))
            {
                url = "http://habrahabr.ru/" + url;
            }

            if (url.startsWith("http://habrahabr.ru/login/") && hasInternet){
                //intent = new Intent(mContext, LoginActivity.class);
            }else if (url.startsWith("http://habrahabr.ru/post/") && hasInternet) {
                intent = new Intent(mContext, PostContentActivity.class);
                //intent.putExtra(PostContentActivity.EXTRA_URL, url);
            /*}else if (url.startsWith("http://habrahabr.ru/hub/") && hasInternet){
                intent = new Intent(mContext, HubContentActivity.class);
                intent.putExtra(HubContentActivity.EXTRA_HUB_URL, url);
            }else if (url.startsWith("http://habrahabr.ru/qa/") && hasInternet){
                intent = new Intent(mContext, QuestionContentActivity.class);
                intent.putExtra(QuestionContentActivity.EXTRA_QUESTION_URL, url);
            }else if (url.startsWith("http://habrahabr.ru/events/") && hasInternet){
                intent = new Intent(mContext, EventContentActivity.class);
                intent.putExtra(EventContentActivity.EVENT_EXTRA_URL, url);
            }else if (url.startsWith("http://habrahabr.ru/company/") && hasInternet){
                intent = new Intent(mContext, CompanyContentActivity.class);
                intent.putExtra(CompanyContentActivity.COMPANY_EXTRA_URL, url);
            }else if (url.startsWith("http://habrahabr.ru/users/") && hasInternet) {
                intent = new Intent(mContext, UserContentActivity.class);
                intent.putExtra(UserContentActivity.USER_EXTRA_URL, url);*/
            }else if (url.contains("youtube.com/watch") && hasInternet){
                String videoId;
                int indexStart = url.indexOf("?v=") + 3;
                videoId = url.substring(indexStart);
                if (videoId.contains("&")){
                    videoId = videoId.substring(0, videoId.indexOf("&"));
                }
                // Notify user about Youtube (or other video-app) launch
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId)));
                    //Toast.makeText(mContext, R.string.youtube_launch, Toast.LENGTH_SHORT).show();
                }catch (ActivityNotFoundException ex){
                    // Try to open video in any possible way if Youtube app is not found
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    //Toast.makeText(mContext, R.string.no_youtube_player, Toast.LENGTH_SHORT).show();
                }
                return true;
            }else {
                if (url.equals("/login/")){
                    url = "http://habrahabr.ru/login/";
                }
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            }

            if (intent != null){
                mContext.startActivity(intent);
            }

            return true;
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            super.onPageFinished(view, url);

            /*if (mScrollPosition > 0){
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        float webViewSize = view.getContentHeight() - view.getTop();
                        float positionInWV = webViewSize * mScrollPosition;
                        int positionY = Math.round(view.getTop() + positionInWV);
                        view.scrollTo(0, positionY);
                    }
                }, 300);
            }*/
        }
    }

    public interface OnScrollListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }

}
