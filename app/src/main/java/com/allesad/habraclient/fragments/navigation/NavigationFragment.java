package com.allesad.habraclient.fragments.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allesad.habraclient.R;
import com.allesad.habraclient.model.navigation.NavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 11/8/2014 4:28 PM.
 */
public class NavigationFragment extends Fragment
{
    //=============================================================
    // Constants
    //=============================================================

    public final static int NAVDRAWER_ITEM_SEPARATOR = -1;
    public final static int NAVDRAWER_ITEM_FEED = 0;
    public final static int NAVDRAWER_ITEM_POSTS = 1;
    public final static int NAVDRAWER_ITEM_BEST = 2;

    //=============================================================
    // Variables
    //=============================================================

    private DrawerLayout mDrawerLayout;
    private ViewGroup mDrawerItemsListContainer;

    private View[] mNavDrawerItemViews;

    private List<NavigationItem> mNavItems;

    //=============================================================
    // Fragment lifecycle
    //=============================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_navdrawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrawerItemsListContainer = (ViewGroup) view.findViewById(R.id.navdrawer_items_list);

        setUpAccountBox();
        setUpNavDrawer();
    }

    //=============================================================
    // Public methods
    //=============================================================

    /*public void setUp(){

    }*/

    //=============================================================
    // Private methods
    //=============================================================

    private void setUpAccountBox(){

    }

    private void setUpNavDrawer(){
        mNavItems = new ArrayList<NavigationItem>();
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_FEED, R.string.menu_feed, R.drawable.ic_hubs));
        //mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_SEPARATOR, 0, 0));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_POSTS, R.string.menu_posts, R.drawable.ic_posts));

        createNavDrawerItems();
    }

    private void createNavDrawerItems(){
        if (mDrawerItemsListContainer == null) {
            return;
        }

        mNavDrawerItemViews = new View[mNavItems.size()];
        mDrawerItemsListContainer.removeAllViews();
        int i = 0;
        for (NavigationItem item : mNavItems) {
            mNavDrawerItemViews[i] = makeNavDrawerItem(item, mDrawerItemsListContainer);
            mDrawerItemsListContainer.addView(mNavDrawerItemViews[i]);
            ++i;
        }
    }

    private View makeNavDrawerItem(final NavigationItem item, ViewGroup container){
        int layoutToInflate = 0;

        if (item.getId() == NAVDRAWER_ITEM_SEPARATOR){
            layoutToInflate = R.layout.li_navdrawer_separator;
        }else{
            layoutToInflate = R.layout.li_navdrawer_item;
        }

        View view = getLayoutInflater(null).inflate(layoutToInflate, container, false);

        if (item.getId() == NAVDRAWER_ITEM_SEPARATOR){
            view.setFocusable(false);
            view.setClickable(false);

            return view;
        }

        ImageView icon = (ImageView) view.findViewById(R.id.navItemView_icon);
        TextView title = (TextView) view.findViewById(R.id.navItemView_title);

        icon.setVisibility(item.getIconResId() > 0 ? View.VISIBLE : View.GONE);
        if (item.getIconResId() > 0){
            icon.setImageResource(item.getIconResId());
        }

        title.setText(item.getTitleResId());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add actual listener
            }
        });

        return view;
    }
}
