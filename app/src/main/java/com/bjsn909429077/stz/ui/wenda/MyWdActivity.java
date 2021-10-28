package com.bjsn909429077.stz.ui.wenda;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomePagerAdapter;
import com.bjsn909429077.stz.adapter.ZjListAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.WenDaZhuanJiaBean;
import com.bjsn909429077.stz.bean.ZjListBean;
import com.bjsn909429077.stz.widget.WrapContentHeightViewPager;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MyWdActivity extends BaseActivity{
    @BindView(R.id.viewpager)
    WrapContentHeightViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;  @BindView(R.id.sort_tb_title)
    TextView sort_tb_title;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    private ArrayList<Fragment> frameList;
    private String type;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_my_wd;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();

        type = intent.getStringExtra("type");

        if (type.equals("我的提问")){
            sort_tb_title.setText("我的提问");
        }else if (type.equals("我的收藏")){
            sort_tb_title.setText("我的收藏");
        }

        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
    }



    @Override
    protected void initData() {
        List<String> listTitle = new ArrayList<>();
        listTitle.add("已回答");
        listTitle.add("未回答");
        intTab(listTitle);

    }
    private void intTab(List<String> listTitle) {
        frameList = new ArrayList<>();
        for (int i = 0; i <listTitle.size() ; i++) {
            Bundle bundle = new Bundle();
            if (i==0){
                bundle.putString("type","1");
            }else {
                bundle.putString("type","0");
            }
            if (type.equals("我的提问")){
                bundle.putString("type1","1");
            }else if (type.equals("我的收藏")){
                bundle.putString("type1","0");
            }
            MyWdFragment myWdFragment = new MyWdFragment();
            myWdFragment.setArguments(bundle);
            frameList.add(myWdFragment);
        }


        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), listTitle, frameList);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);//此方法就是让tablayout和ViewPager联动
        viewpager.setOffscreenPageLimit(listTitle.size());

        for (int x = 0; x < listTitle.size(); x++) {
            TabLayout.Tab tab = tablayout.getTabAt(x);
            TextView textView = (TextView) View.inflate(this, R.layout.tab_bg2, null);
            textView.setText(listTitle.get(x));
            tab.setCustomView(textView);
            TextPaint textViewPaint = textView.getPaint();
            if (x==0){
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_5F7DFF));
            }
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                View view = tab.getCustomView();

                if (null != view && view instanceof TextView) {
                    TextView textView = view.findViewById(R.id.choose_icon_tab_tv);
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_5F7DFF));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    TextView textView = view.findViewById(R.id.choose_icon_tab_tv);
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (tablayout));

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

}