package com.bjsn909429077.stz.ui.wenda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomePageFragmentAdapter;
import com.bjsn909429077.stz.adapter.HomePagerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeBannerBean2;
import com.bjsn909429077.stz.bean.HomeRecommendBean;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.bean.HomeRecommendtypeBean;
import com.bjsn909429077.stz.bean.ShowFragment;
import com.bjsn909429077.stz.ui.MyApplication;
import com.bjsn909429077.stz.ui.home.HomePageFragment;
import com.bjsn909429077.stz.ui.home.SortActivity;
import com.bjsn909429077.stz.ui.home.SortGuideActivity;
import com.bjsn909429077.stz.ui.home.contract.SortHomeContract;
import com.bjsn909429077.stz.ui.home.model.SortHomeModel;
import com.bjsn909429077.stz.widget.WrapContentHeightViewPager;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.StatusBarUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.stx.xhb.xbanner.XBanner;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;

public class WenDaActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    WrapContentHeightViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    private int position = -1;
    private ArrayList<Fragment> frameList;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_wenda;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (getIntent()!=null){
            position = intent.getIntExtra("from",-1);
        }
        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void initData() {
        List<String> listTitle = new ArrayList<>();
        listTitle.add("推荐");
        listTitle.add("最新");
        listTitle.add("专家");
        intTab(listTitle);
    }

    private void intTab(List<String> listTitle) {
        frameList = new ArrayList<>();
        WenDaHomeFragment homePageFragment = new WenDaHomeFragment();
        WenDaNewFragment wenDaNewFragment = new WenDaNewFragment();
        WenDaZhuanjiaFragment wenDaZhuanjiaFragment = new WenDaZhuanjiaFragment();
        frameList.add(homePageFragment);
        frameList.add(wenDaNewFragment);
        frameList.add(wenDaZhuanjiaFragment);

        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), listTitle, frameList);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);//此方法就是让tablayout和ViewPager联动
        viewpager.setOffscreenPageLimit(listTitle.size());

        for (int x = 0; x < listTitle.size(); x++) {
            TabLayout.Tab tab = tablayout.getTabAt(x);
            View inflate = View.inflate(this, R.layout.tab_bg1, null);
            TextView textView = inflate.findViewById(R.id.choose_icon_tab_tv);
            LinearLayout iv = inflate.findViewById(R.id.iv_down);
            textView.setText(listTitle.get(x));
            tab.setCustomView(inflate);
            //  tablayout.addTab(tab);
            TextPaint textViewPaint = textView.getPaint();
            if (x == 0) {
                iv.setVisibility(View.VISIBLE);
                textView.setPadding(dip2px(30), 0, 0, 0);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_selected));
                textViewPaint.setFakeBoldText(true);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (frameList != null && frameList.size() > 0) {
                            if (frameList.get(0) instanceof WenDaHomeFragment) {
                                WenDaHomeFragment wenDaHomeFragment = (WenDaHomeFragment) frameList.get(0);
                                if (wenDaHomeFragment.isVisibleToUser){
                                   wenDaHomeFragment.showRiskAreaPopupWindow(tablayout);
                                }
                            }
                        }
                    }
                });


            } else {
                iv.setVisibility(View.GONE);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                textViewPaint.setFakeBoldText(false);
            }

        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                View view = tab.getCustomView();

                if (null != view && view instanceof LinearLayout) {
                    TextView textView = view.findViewById(R.id.choose_icon_tab_tv);
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_selected));
                    TextPaint paint = textView.getPaint();
                    paint.setFakeBoldText(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof LinearLayout) {
                    TextView textView = view.findViewById(R.id.choose_icon_tab_tv);
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                    TextPaint paint = textView.getPaint();
                    paint.setFakeBoldText(false);
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

        if (position!=-1)
        {
                    tablayout.getTabAt(position).select(); //默认选中某项放在加载viewpager之后
        }



    }
   /* private void getListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("firstTypeId",id);
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeRecommendList, map, true,
                new NovateUtils.setRequestReturn<HomeRecommendListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeRecommendListBean homeRecommendListBean) {
                        if (homeRecommendListBean != null) {
                            if (homeRecommendListBean.getData()!=null&&homeRecommendListBean.getData().size()>0){
                                List<HomeRecommendListBean.DataBean> data = homeRecommendListBean.getData();
                                if (data.size()>3){
                                    data = data.subList(0, 3);
                                }
                                HomePageFragmentAdapter  homePageFragmentAdapter = new HomePageFragmentAdapter(R.layout.item_home_remmcond,data, WenDaActivity.this);
                                recyclerView.setAdapter(homePageFragmentAdapter);

                            }

                        }
                    }
                });

    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //筛选
        if (requestCode == Constant.REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
    }

       @OnClick({R.id.tv_right})
       public void onClick(View view) {
           switch (view.getId()) {
               case R.id.tv_right:
                   Intent intent = new Intent(this, QuestionActivity.class);
                   intent.putExtra("from","tiwen");
                   startActivity(intent);
                   break;

           }
       }


    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}