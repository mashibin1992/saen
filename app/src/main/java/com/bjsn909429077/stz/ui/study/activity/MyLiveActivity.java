package com.bjsn909429077.stz.ui.study.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.ui.study.fragment.AllLiveFragment;
import com.bjsn909429077.stz.ui.study.fragment.LiveCourseChildFragment;
import com.bjsn909429077.stz.ui.study.fragment.PlaybackLiveFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class MyLiveActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_my_live;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle(R.string.live_course_title);
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("全部直播");
        strings.add("直播回放");
        strings.add("直播课程");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllLiveFragment());
        fragments.add(new PlaybackLiveFragment());
        fragments.add(new LiveCourseChildFragment());
        CourseViewPagerAdapter courseViewPagerAdapter = new CourseViewPagerAdapter(strings, fragments, getSupportFragmentManager());
        view_pager.setAdapter(courseViewPagerAdapter);
        view_pager.setOffscreenPageLimit(strings.size());
        tab_layout.setupWithViewPager(view_pager);
    }

    @Override
    protected void initData() {

    }
}