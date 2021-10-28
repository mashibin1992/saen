package com.bjsn909429077.stz.ui.questionbank.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.bjsn909429077.stz.ui.questionbank.AllErrorQuestionFragment;
import com.bjsn909429077.stz.ui.questionbank.TypeErrorQuestionFragment;
import com.google.android.material.tabs.TabLayout;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ErrorQuestionTabAdapter;
import com.jiangjun.library.ui.base.BaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ErrorQuestionBookActivity extends BaseActivity {

    private ViewPager error_type_vp;
    private TabLayout error_type_tl;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_error_question_book;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("错题本");
        error_type_tl = findViewById(R.id.error_type_tl);
        error_type_vp = findViewById(R.id.error_type_vp);
    }

    @Override
    protected void initData() {
        ArrayList<String> tabList = new ArrayList<>();
        tabList.add("章节");
        tabList.add("试卷");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllErrorQuestionFragment());
        fragments.add(new TypeErrorQuestionFragment());
        ErrorQuestionTabAdapter errorQuestionTabAdapter = new ErrorQuestionTabAdapter(getSupportFragmentManager(), tabList, fragments);
        error_type_vp.setAdapter(errorQuestionTabAdapter);
        error_type_tl.setupWithViewPager(error_type_vp);
    }

}