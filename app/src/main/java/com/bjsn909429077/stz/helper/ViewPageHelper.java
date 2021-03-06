package com.bjsn909429077.stz.helper;

import com.bjsn909429077.stz.widget.MScroller;

import java.lang.reflect.Field;

import androidx.viewpager.widget.ViewPager;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/7 14:23
 **/
public class ViewPageHelper {
    ViewPager viewPager;

    MScroller scroller;

    public ViewPageHelper(ViewPager viewPager) {
        this.viewPager = viewPager;
        init();
    }
    public void setCurrentItem(int item){
        setCurrentItem(item,true);
    }

    public MScroller getScroller() {
        return scroller;
    }


    public void setCurrentItem(int item, boolean somoth){
        int current=viewPager.getCurrentItem();
        //如果页面相隔大于1,就设置页面切换的动画的时间为0
        if(Math.abs(current-item)>1){
            scroller.setMDuration(0);
            viewPager.setCurrentItem(item,somoth);
            scroller.setMDuration(300);
        }else{
            scroller.setMDuration(300);
            viewPager.setCurrentItem(item,somoth);
        }
    }

    private void init() {
        scroller = new MScroller(viewPager.getContext());
        Class<ViewPager> cl = ViewPager.class;
        try {
            Field field = cl.getDeclaredField("mScroller");
            field.setAccessible(true);
            //利用反射设置mScroller域为自己定义的MScroller
            field.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

