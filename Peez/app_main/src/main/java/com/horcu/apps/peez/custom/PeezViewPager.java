package com.horcu.apps.peez.custom;

/**
 * Created by Horatio on 3/18/2016.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PeezViewPager extends ViewPager {

    private boolean enabled;

    public PeezViewPager(Context context) {
        super(context);
        this.enabled = true;
    }

    public PeezViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(this.enabled)
        return super.onInterceptTouchEvent(event);

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.enabled)
            return super.onTouchEvent(event);

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}