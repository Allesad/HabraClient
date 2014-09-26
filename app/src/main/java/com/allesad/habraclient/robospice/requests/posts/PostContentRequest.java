package com.allesad.habraclient.robospice.requests.posts;

import android.text.TextUtils;

import com.allesad.habraclient.HabraClientApplication;
import com.allesad.habraclient.R;
import com.allesad.habraclient.helpers.RequestHelper;
import com.allesad.habraclient.model.posts.PostContentData;
import com.allesad.habraclient.robospice.response.posts.PostContentResponse;
import com.allesad.habraclient.utils.Logger;
import com.octo.android.robospice.request.SpiceRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Allesad on 08.04.2014.
 */
public class PostContentRequest extends SpiceRequest<PostContentResponse> {

    private int mPostId;

    public PostContentRequest(int postId) {
        super(PostContentResponse.class);

        mPostId = postId;
    }

    @Override
    public PostContentResponse loadDataFromNetwork() throws Exception {
        PostContentResponse response;

        try {
            response = RequestHelper.getPost(mPostId);
            PostContentData post = response.getPost();
            long start = System.currentTimeMillis();
            post.setContent(formatPostContent(post));
            long duration = System.currentTimeMillis() - start;
            Logger.v("Content formatting duration: " + duration + "ms");
        }catch (IOException ex){
            response = new PostContentResponse();
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    //=============================================================
    // Private methods
    //=============================================================

    private String formatPostContent(PostContentData post){
        String content = post.getContent();
        content = parseDocument(content);
        content = addTitleInfo(post, content);
        content = applyWrapperInfo(content);

        return content;
    }

    private String parseDocument(String data){
        Document document = Jsoup.parse(data);

        // Extract images
        Elements images = document.select("img");

        for (Element image : images){
            image.setBaseUri("http://beta.hstor.org/");
            String url = image.absUrl("src");
            url = url.replace("http://habrastorage.org", "http://beta.hstor.org");
            image.attr("src", url);
            /*if (!TextUtils.isEmpty(url)){
                mImagePaths.add(url);
            }*/
        }

        // Find all youtube frames on the page and replace them with video preview
        Elements iframes = document.select("iframe");

        for (Element iframe : iframes){
            if (iframe.hasAttr("src") && iframe.attr("src").contains("www.youtube.com/embed")){
                String videoId = iframe.attr("src").substring(iframe.attr("src").lastIndexOf("/") + 1);
                videoId = videoId.substring(0, videoId.indexOf("?"));
                String videoUrl = "http://www.youtube.com/watch?v=" + videoId;
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
            object.wrap("<center><a href=\"" + source + "\" class=\"btnVideo\">" + HabraClientApplication.getAppContext().getString(R.string.open_video) + "</a></center>");
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
}
