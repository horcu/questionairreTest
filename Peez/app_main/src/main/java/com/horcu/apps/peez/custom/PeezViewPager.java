package com.horcu.apps.peez.custom;

/**
 * Created by Horatio on 3/18/2016.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PeezViewPager extends ViewPager {

    public PeezViewPager(Context context) {
        super(context);
    }

    public PeezViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages if the event passed in is null
        if(event == null)
            return false;
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages if the event passed in is null
        if(event == null)
            return false;
        return super.onTouchEvent(event);
    }
}