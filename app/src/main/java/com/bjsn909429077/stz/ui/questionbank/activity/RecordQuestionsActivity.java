package com.bjsn909429077.stz.ui.questionbank.activity;

import android.os.Bundle;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ErrorQuestionTabAdapter;
import com.bjsn909429077.stz.ui.questionbank.ChapterRecordQuestionsFragment;
import com.bjsn909429077.stz.ui.questionbank.TestRecordQuestionsFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class RecordQuestionsActivity extends BaseActivity {

    private ViewPager record_type_vp;
    private TabLayout record_type_tl;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_record_questions;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("做题记录");
        record_type_tl = findViewById(R.id.record_type_tl);
        record_type_vp = findViewById(R.id.record_type_vp);
    }

    @Override
    protected void initData() {
        ArrayList<String> tabList = new ArrayList<>();
        tabList.add("章节");
        tabList.add("试卷");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChapterRecordQuestionsFragment());
        fragments.add(new TestRecordQuestionsFragment());
        ErrorQuestionTabAdapter errorQuestionTabAdapter = new ErrorQuestionTabAdapter(getSupportFragmentManager(), tabList, fragments);
        record_type_vp.setAdapter(errorQuestionTabAdapter);
        record_type_tl.setupWithViewPager(record_type_vp);
    }
}