package com.allesad.habraclient.components;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Allesad on 08.04.2014.
 */
public class ViewPagerExtended extends ViewPager {

    //=============================================================
    // Constructor
    //=============================================================
    public ViewPagerExtended(Context context) {
        super(context);
    }

    public ViewPagerExtended(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //=============================================================
    // Overridden methods
    //=============================================================

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /*@Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof TouchImageView){
            return ((TouchImageView) v).canScrollHorizontallyFroyo(dx);
        }else{
            return super.canScroll(v, checkV, dx, x, y);
        }
    }*/
}
