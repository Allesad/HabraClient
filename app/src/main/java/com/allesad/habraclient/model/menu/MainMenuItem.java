package com.allesad.habraclient.model.menu;

import android.content.Context;

import com.allesad.habraclient.R;
import com.allesad.habraclient.utils.Enums;

/**
 * Created by Allesad on 23.03.2014.
 */
public class MainMenuItem {

    private Enums.MainMenu type;
    private String title;
    private int icon;
    private boolean section;

    public MainMenuItem(Context context, Enums.MainMenu type){
        this.type = type;
        this.icon = R.drawable.ic_launcher;
        this.section = false;

        switch (type)
        {
            case POSTS:
                title = context.getString(R.string.menu_posts);

                break;
            case HUBS:
                title = context.getString(R.string.menu_hubs);
                break;
            case QUESTIONS:
                title = context.getString(R.string.menu_qa);
                break;
            case EVENTS:
                title = context.getString(R.string.menu_events);
                break;
            case COMPANIES:
                title = context.getString(R.string.menu_companies);
                break;
            case USERS:
                title = context.getString(R.string.menu_users);
                break;
        }
    }

    public Enums.MainMenu getType() {
        return type;
    }

    public void setType(Enums.MainMenu mType) {
        this.type = mType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int mIcon) {
        this.icon = mIcon;
    }

    public boolean isSection() {
        return section;
    }

    public void setSection(boolean mSection) {
        this.section = mSection;
    }
}
