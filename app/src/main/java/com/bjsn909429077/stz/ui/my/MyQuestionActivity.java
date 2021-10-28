package com.bjsn909429077.stz.ui.my;

import android.os.Bundle;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ErrorQuestionTabAdapter;
import com.bjsn909429077.stz.ui.my.fragment.AnsweredFragment;
import com.bjsn909429077.stz.ui.my.fragment.UnansweredFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MyQuestionActivity extends BaseActivity {

    private ViewPager my_question_list_vp;
    private TabLayout my_question_list_tl;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_my_question;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("我的提问");
        my_question_list_tl = findViewById(R.id.my_question_list_tl);
        my_question_list_vp = findViewById(R.id.my_question_list_vp);
    }

    @Override
    protected void initData() {
        ArrayList<String> tabList = new ArrayList<>();
        tabList.add("已回答");
        tabList.add("未回答");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AnsweredFragment());
        fragments.add(new UnansweredFragment());
        ErrorQuestionTabAdapter errorQuestionTabAdapter = new ErrorQuestionTabAdapter(getSupportFragmentManager(), tabList, fragments);
        my_question_list_vp.setAdapter(errorQuestionTabAdapter);
        my_question_list_tl.setupWithViewPager(my_question_list_vp);
    }

}