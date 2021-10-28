package com.bjsn909429077.stz.ui.study.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.bean.ShowFragment;
import com.bjsn909429077.stz.ui.home.HomeLiveListFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;

public class LiveList_2Activity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_live;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("今日");
        strings.add("全部");
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", strings.size() - i - 1);
            fragments.add(HomeLiveListFragment.getInstance(bundle));
        }
        CourseViewPagerAdapter courseViewPagerAdapter = new CourseViewPagerAdapter(strings, fragments, getSupportFragmentManager());
        view_pager.setAdapter(courseViewPagerAdapter);
        view_pager.setOffscreenPageLimit(strings.size());
        tab_layout.setupWithViewPager(view_pager);
        tool_bar.setNavigationOnClickListener(v -> {
            RxBus.getDefault().post(new ShowFragment(ShowFragment.HOME_FRAGMENT));
            finish();
        });
    }

    @Override
    protected void initData() {

    }
}