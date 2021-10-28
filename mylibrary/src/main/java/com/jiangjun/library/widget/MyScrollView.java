//package com.jiangjun.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
//
//public class MyScrollView extends ScrollView {
//    private boolean isUp = true;
//
//    public MyScrollView(Context context) {
//        super(context);
//    }
//
//    public MyScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_HOVER_MOVE:
//                if (ev.getY() == 0) {
//                    return super.onTouchEvent(ev);
//                }
//                break;
//        }
//        if (isUp) {
//
//        } else {
//            return false;
//        }
//    }
//}
