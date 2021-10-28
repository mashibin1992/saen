package com.bjsn909429077.stz.ui.my;

import android.os.Bundle;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ErrorQuestionTabAdapter;
import com.bjsn909429077.stz.ui.my.fragment.AllOrderFragment;
import com.bjsn909429077.stz.ui.my.fragment.AlreadyPayOrderFragment;
import com.bjsn909429077.stz.ui.my.fragment.WitePayOrderFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MyOrderListActivity extends BaseActivity {

    private ViewPager order_list_vp;
    private TabLayout order_list_tl;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_my_order_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("订单列表");
        order_list_tl = findViewById(R.id.order_list_tl);
        order_list_vp = findViewById(R.id.order_list_vp);
    }

    @Override
    protected void initData() {
        ArrayList<String> tabList = new ArrayList<>();
        tabList.add("全部");
        tabList.add("待支付");
        tabList.add("已支付");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllOrderFragment());
        fragments.add(new WitePayOrderFragment());
        fragments.add(new AlreadyPayOrderFragment());
        ErrorQuestionTabAdapter errorQuestionTabAdapter = new ErrorQuestionTabAdapter(getSupportFragmentManager(), tabList, fragments);
        order_list_vp.setAdapter(errorQuestionTabAdapter);
        order_list_tl.setupWithViewPager(order_list_vp);
    }
}