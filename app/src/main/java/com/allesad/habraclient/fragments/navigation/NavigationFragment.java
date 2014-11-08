package com.allesad.habraclient.fragments.navigation;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
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

    public final static int NAVDRAWER_ITEM_SECTION = -2;
    public final static int NAVDRAWER_ITEM_SEPARATOR = -1;
    public final static int NAVDRAWER_ITEM_HABR_FEED = 0;
    public final static int NAVDRAWER_ITEM_HABR_POSTS = 1;
    public final static int NAVDRAWER_ITEM_HABR_BEST = 2;
    public final static int NAVDRAWER_ITEM_GT_FEED = 5;
    public final static int NAVDRAWER_ITEM_GT_POSTS = 6;
    public final static int NAVDRAWER_ITEM_SETTINGS = 10;
    public final static int NAVDRAWER_ITEM_ABOUT = 11;

    //=============================================================
    // Variables
    //=============================================================

    private DrawerLayout mDrawerLayout;
    private ViewGroup mDrawerItemsListContainer;

    private View[] mNavDrawerItemViews;

    private INavigationListener mListener;

    private List<NavigationItem> mNavItems;

    private int mSelectedItemId;

    //=============================================================
    // Fragment lifecycle
    //=============================================================

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (INavigationListener) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement " + INavigationListener.class.getSimpleName() + " interface!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectedItemId = NAVDRAWER_ITEM_HABR_FEED;
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

    public void setUp(DrawerLayout drawerLayout){
        mDrawerLayout = drawerLayout;
    }

    //=============================================================
    // Private methods
    //=============================================================

    private void setUpAccountBox(){

    }

    private void setUpNavDrawer(){
        mNavItems = new ArrayList<NavigationItem>();
        // Habrahabr
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_SECTION, R.string.nav_section_habr, 0));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_HABR_FEED, R.string.menu_feed, R.drawable.ic_hubs));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_HABR_POSTS, R.string.menu_posts, R.drawable.ic_posts));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_SEPARATOR, 0, 0));
        // Geek Times
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_SECTION, R.string.nav_section_geektimes, 0));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_GT_FEED, R.string.menu_feed, R.drawable.ic_hubs));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_GT_POSTS, R.string.menu_posts, R.drawable.ic_posts));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_SEPARATOR, 0, 0));
        // Settings
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_SETTINGS, R.string.nav_settings, R.drawable.ic_settings));
        mNavItems.add(new NavigationItem(NAVDRAWER_ITEM_ABOUT, R.string.nav_about, R.drawable.ic_info));

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
        int layoutToInflate;

        if (item.getId() == NAVDRAWER_ITEM_SEPARATOR){
            layoutToInflate = R.layout.li_navdrawer_separator;
        }else if (item.getId() == NAVDRAWER_ITEM_SECTION) {
            layoutToInflate = R.layout.li_navdrawer_section;
        }else{
            layoutToInflate = R.layout.li_navdrawer_item;
        }

        View view = getLayoutInflater(null).inflate(layoutToInflate, container, false);

        if (item.getId() == NAVDRAWER_ITEM_SEPARATOR || item.getId() == NAVDRAWER_ITEM_SECTION){
            view.setFocusable(false);
            view.setClickable(false);
        }

        if (item.getId() == NAVDRAWER_ITEM_SEPARATOR){
            return view;
        }

        ImageView icon = (ImageView) view.findViewById(R.id.navItemView_icon);
        TextView title = (TextView) view.findViewById(R.id.navItemView_title);

        if (icon != null){
            icon.setVisibility(item.getIconResId() > 0 ? View.VISIBLE : View.GONE);
            if (item.getIconResId() > 0){
                icon.setImageResource(item.getIconResId());
            }
        }

        title.setText(item.getTitleResId());

        if (item.getId() == NAVDRAWER_ITEM_SECTION){
            return view;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNavigationItem(item);
            }
        });

        formatNavDrawerItem(icon, title, mSelectedItemId == item.getId());

        return view;
    }

    private void formatNavDrawerItem(ImageView icon, TextView title, boolean selected){
        Resources res = getResources();
        title.setTextColor(selected ?
                res.getColor(R.color.navdrawer_text_color_selected) :
                res.getColor(R.color.navdrawer_text_color));
        icon.setColorFilter(selected ?
                res.getColor(R.color.navdrawer_icon_tint_selected) :
                res.getColor(R.color.navdrawer_icon_tint));
    }

    private void selectNavigationItem(NavigationItem item){
        if (item.getId() == mSelectedItemId){
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        mSelectedItemId = item.getId();
        updateNavigationItems();
        if (mListener != null){
            mListener.onNavigationItemClicked(mSelectedItemId);
        }
    }

    private void updateNavigationItems(){
        int viewsCount = mNavDrawerItemViews.length;
        View view;
        ImageView icon;
        TextView title;
        NavigationItem navItem;
        for (int i = 0; i < viewsCount; i++) {
            view = mNavDrawerItemViews[i];
            navItem = mNavItems.get(i);

            icon = (ImageView) view.findViewById(R.id.navItemView_icon);
            title = (TextView) view.findViewById(R.id.navItemView_title);

            if (icon != null && title != null){
                formatNavDrawerItem(icon, title, navItem.getId() == mSelectedItemId);
            }
        }
    }

    public interface INavigationListener {
        public void onNavigationItemClicked(final int itemId);
    }
}
