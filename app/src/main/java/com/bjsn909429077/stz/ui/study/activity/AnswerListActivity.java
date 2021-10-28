package com.bjsn909429077.stz.ui.study.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.ui.study.fragment.MyAnswerFragment;
import com.bjsn909429077.stz.ui.study.fragment.RelevantAnswerFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class AnswerListActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_answer_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle("课程答疑");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("我的答疑");
        strings.add("相关答疑");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyAnswerFragment());
        fragments.add(new RelevantAnswerFragment());
        CourseViewPagerAdapter courseViewPagerAdapter = new CourseViewPagerAdapter(strings, fragments, getSupportFragmentManager());
        view_pager.setAdapter(courseViewPagerAdapter);
        tab_layout.setupWithViewPager(view_pager);
        LinearLayout linearLayout = (LinearLayout) tab_layout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.shape_line_vertical));
//        linearLayout.setDividerPadding(20);
    }

    @Override
    protected void initData() {

    }
}
