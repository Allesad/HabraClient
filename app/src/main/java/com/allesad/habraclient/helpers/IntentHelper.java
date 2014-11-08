package com.allesad.habraclient.helpers;

import android.content.Context;
import android.content.Intent;

import com.allesad.habraclient.activities.PhotoGalleryActivity;
import com.allesad.habraclient.activities.PostContentActivity;
import com.allesad.habraclient.utils.ArgumentConstants;

import java.util.ArrayList;

/**
 * Created by Allesad on 23.03.2014.
 */
public class IntentHelper {

    public static void toSettingsScreen(Context context){

    }

    public static void toPostContentScreen(Context context, long postId){
        Intent intent = new Intent(context, PostContentActivity.class);
        intent.putExtra(ArgumentConstants.POST_ID, postId);
        context.startActivity(intent);
    }

    public static void toPostGalleryScreen(Context context, ArrayList<String> imagePaths, String currentImage){
        Intent intent = new Intent(context, PhotoGalleryActivity.class);
        intent.putStringArrayListExtra(ArgumentConstants.IMAGE_PATHS, imagePaths);
        intent.putExtra(ArgumentConstants.SELECTED_IMAGE, currentImage);
        context.startActivity(intent);
    }

    public static void toSearchScreen(Context context){

    }

    public static void toUserProfileScreen(Context context){

    }
}
