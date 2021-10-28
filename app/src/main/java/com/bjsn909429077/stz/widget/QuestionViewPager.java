package com.bjsn909429077.stz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bjsn909429077.stz.helper.ViewPageHelper;

import java.lang.reflect.Field;

import androidx.viewpager.widget.ViewPager;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/7 14:17
 **/
public class QuestionViewPager extends ViewPager {
    private static final int Back_Duration = 500;
    private ViewPageHelper helper;

    public QuestionViewPager(Context context) {
        this(context, null);
    }


    public QuestionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setReadEffect();
        setScrollerDuration();
        helper = new ViewPageHelper(this);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    private void setScrollerDuration() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            MScroller scroller = new MScroller(getContext());
            field.set(this, scroller);
            scroller.setMDuration(0);
        } catch (Exception e) {
            Log.e("@", "", e);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        MScroller scroller = helper.getScroller();
        if (Math.abs(getCurrentItem() - item) > 1) {
            scroller.setMDuration(0);
            super.setCurrentItem(item, smoothScroll);
            scroller.setMDuration(Back_Duration);
        } else {
            scroller.setMDuration(Back_Duration);
            super.setCurrentItem(item, smoothScroll);
        }
    }

    public void setReadEffect() {
        setPageTransformer(true, new PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                if (position < -1) {
                    view.setAlpha(0);

                } else if (position <= 0) {
                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);

                } else if (position <= 1) {
                    view.setAlpha(1);
                    view.setTranslationX(pageWidth * -position);

                } else {
                    view.setAlpha(0);
                }
            }
        });
    }
}
