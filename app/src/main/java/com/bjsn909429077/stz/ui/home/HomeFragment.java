package com.bjsn909429077.stz.ui.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomeKingAdapter;
import com.bjsn909429077.stz.adapter.HomePagerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeBannerBean;
import com.bjsn909429077.stz.bean.HomeKingBean;
import com.bjsn909429077.stz.bean.HomeLiveBean;
import com.bjsn909429077.stz.bean.HomeRecommendBean;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.bean.HomeRecommendtypeBean;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.order.OrderLiveActivity;
import com.bjsn909429077.stz.ui.study.activity.LiveList_2Activity;
import com.bjsn909429077.stz.ui.wenda.WenDaActivity;
import com.bjsn909429077.stz.widget.PageView;
import com.bjsn909429077.stz.widget.WrapContentHeightViewPager;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseLazyLoadFragment implements PageView.OnSelectPageListener {

    @BindView(R.id.xBanner)
    XBanner xBanner;
    @BindView(R.id.pageView)
    PageView pageView;
    @BindView(R.id.viewpager)
    WrapContentHeightViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.view_tab0)
    View view_tab0;
    @BindView(R.id.view_tab1)
    View view_tab1;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.iv_header1)
    ImageView iv_header1;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_type1)
    TextView tv_type1;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_time1)
    TextView tv_time1;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_name1)
    TextView tv_name1;
    @BindView(R.id.tv_pirce)
    TextView tv_pirce;
    @BindView(R.id.tv_pirce1)
    TextView tv_pirce1;
    @BindView(R.id.tv_botton)
    TextView tv_botton;
    @BindView(R.id.tv_botton1)
    TextView tv_botton1;
    @BindView(R.id.rely_view)
    RelativeLayout rely_view;
    @BindView(R.id.rely_xuanke)
    RelativeLayout rely_xuanke;
    @BindView(R.id.rely_live)
    RelativeLayout rely_live;
    @BindView(R.id.linear_dayi)
    LinearLayout linear_dayi;    @BindView(R.id.constraint)
    ConstraintLayout constraint;
    private List<HomeKingBean.DataBean> kingData;
    private HomeKingAdapter kingAdapter1;
    private HomeKingAdapter kingAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private int liveMould0;
    private int liveMould1;
    private String price0;
    private String price1;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        pageView.setSelectListener(this);
    }


    private void intTab(List<HomeRecommendBean.DataBean> data) {
        ArrayList<Fragment> frameList = new ArrayList<>();
        List<String> listTiele = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            listTiele.add(data.get(i).getName());
            HomePageFragment homePageFragment = new HomePageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("typeId", data.get(i).getId());
            homePageFragment.setArguments(bundle);
            frameList.add(homePageFragment);
        }
        HomePagerAdapter adapter = new HomePagerAdapter(getChildFragmentManager(), listTiele, frameList);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);//此方法就是让tablayout和ViewPager联动
        viewpager.setOffscreenPageLimit(listTiele.size());

        for (int x = 0; x < listTiele.size(); x++) {
            TabLayout.Tab tab = tablayout.getTabAt(x);
            View inflate = View.inflate(getActivity(), R.layout.tab_bg, null);
            TextView textView = inflate.findViewById(R.id.choose_icon_tab_tv);
            textView.setText(listTiele.get(x));
            tab.setCustomView(inflate);
            //  tablayout.addTab(tab);
            TextPaint textViewPaint = textView.getPaint();
            if (x == 0) {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_selected));
                //textView.setSelected(true);
                textView.setTextSize(19);
                textViewPaint.setFakeBoldText(true);
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                //textView.setSelected(false);
                textView.setTextSize(16);
                textViewPaint.setFakeBoldText(false);
            }
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_selected));
                    TextPaint paint = ((TextView) view).getPaint();
                    ((TextView) view).setTextSize(19);
                    paint.setFakeBoldText(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                    TextPaint paint = ((TextView) view).getPaint();
                    paint.setFakeBoldText(false);
                    ((TextView) view).setTextSize(16);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (tablayout));
    }

    @Override
    protected void loadData() {
        //金刚区
        getBannerData();
        getKingData();
        //直播列表
        getLiveData();
        //课程分类
        getRecommendData();

        //名师答疑列表
        getRecommendList();
    }

    private void getRecommendData() {
        //api/app/v1/index/course/recommend/type/list
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeRecommend, map, true,
                new NovateUtils.setRequestReturn<HomeRecommendBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeRecommendBean homeRehcommendBean) {
                        if (homeRehcommendBean != null) {
                            List<HomeRecommendBean.DataBean> data = homeRehcommendBean.getData();
                            if (data != null && data.size() > 0) {
                                intTab(data);
                                //获取课程分类数据


                            }
                        }
                    }
                });

    }

    //名师答疑列表
    private void getRecommendList() {
        //api/app/v1/index/wd/recommend/list
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeRecommendType, map, true,
                new NovateUtils.setRequestReturn<HomeRecommendtypeBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeRecommendtypeBean homeRecommendtypeBean) {
                        if (homeRecommendtypeBean != null) {
                            List<HomeRecommendtypeBean.DataBean> data = homeRecommendtypeBean.getData();
                            if (data != null && data.size() > 0) {
                                constraint.setVisibility(View.VISIBLE);
                                linear_dayi.removeAllViews();
                                /*   data.get(0).setType(2);*/
                                for (int i = 0; i < data.size(); i++) {
                                    int type = data.get(i).getType();
                                    //1.热门 > 2推荐> 3普通
                                    if (type == 1) {
                                        View inflate1 = LayoutInflater.from(mContext).inflate(R.layout.item_home_recommend1, null);
                                        LinearLayout linear_1 = inflate1.findViewById(R.id.linear_1);
                                        TextView tv_type_dayi = inflate1.findViewById(R.id.tv_type_dayi);
                                        TextView tv_title = inflate1.findViewById(R.id.tv_title);
                                        TextView tv_dayi = inflate1.findViewById(R.id.tv_dayi);
                                        ImageView iv_1 = inflate1.findViewById(R.id.iv_1);
                                        ImageView iv_2 = inflate1.findViewById(R.id.iv_2);
                                        ImageView iv_3 = inflate1.findViewById(R.id.iv_3);
                                        tv_type_dayi.setText("热门");
                                        tv_type_dayi.setBackgroundResource(R.drawable.bg_ffeaea_c4);
                                        tv_type_dayi.setTextColor(Color.parseColor("#DA5C5C"));
                                        if (!TextUtils.isEmpty(data.get(i).getQuestionName())) {
                                            tv_title.setText(data.get(i).getQuestionName());
                                        }
                                        if (!TextUtils.isEmpty(data.get(i).getAnswerContent())) {
                                            tv_dayi.setText(data.get(i).getAnswerContent());

                                        }

                                        if (data.get(i).getAnswerPics() != null && data.get(i).getAnswerPics().size() > 0) {
                                            List<String> answerPics = data.get(i).getAnswerPics();
                                            if (answerPics.size() == 1 && !TextUtils.isEmpty(answerPics.get(0))) {
                                                Glide.with(getActivity()).load(answerPics.get(0)).into(iv_1);
                                            }
                                            if (answerPics.size() == 2 && !TextUtils.isEmpty(answerPics.get(1))) {
                                                Glide.with(getActivity()).load(answerPics.get(1)).into(iv_2);
                                            } else {
                                                iv_2.setVisibility(View.INVISIBLE);
                                                iv_3.setVisibility(View.INVISIBLE);
                                            }
                                            if (answerPics.size() == 3 && !TextUtils.isEmpty(answerPics.get(2))) {
                                                Glide.with(getActivity()).load(answerPics.get(2)).into(iv_3);
                                            } else {
                                                iv_3.setVisibility(View.INVISIBLE);
                                            }
                                        }

                                        int finalI = i;
                                        linear_1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ToastUtils.Toast(getActivity(), "名师答疑" + data.get(finalI).getQuestionName());
                                            }
                                        });

                                        linear_dayi.addView(inflate1);
                                    }
                                    if (i == data.size() - 1) {
                                        if (linear_dayi.getChildCount() != 2) {
                                            for (int j = 0; j < data.size(); j++) {
                                                int type1 = data.get(j).getType();
                                                //1.热门 > 2推荐> 3普通
                                                if (type1 == 2 || type1 == 3) {

                                                    View inflate2 = LayoutInflater.from(mContext).inflate(R.layout.item_home_recommend2, null);
                                                    LinearLayout linear_2 = inflate2.findViewById(R.id.linear_2);
                                                    TextView tv_type_dayi1 = inflate2.findViewById(R.id.tv_type_dayi1);

                                                    tv_type_dayi1.setBackgroundResource(R.drawable.bg_eaeeff_c4);
                                                    tv_type_dayi1.setTextColor(Color.parseColor("#5F7DFF"));

                                                    TextView tv_title1 = inflate2.findViewById(R.id.tv_title1);
                                                    TextView tv_dayi1 = inflate2.findViewById(R.id.tv_dayi1);
                                                    if (type1 == 2) {
                                                        tv_type_dayi1.setText("推荐");
                                                    } else if (type1 == 3) {
                                                        tv_type_dayi1.setText("问答");
                                                    }
                                                    tv_title1.setText(data.get(i).getQuestionName());
                                                    tv_dayi1.setText(data.get(i).getAnswerContent());
                                                    linear_dayi.addView(inflate2);

                                                    int finalI1 = i;
                                                    linear_2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            ToastUtils.Toast(getActivity(), "名师答疑" + data.get(finalI1).getQuestionName());
                                                        }
                                                    });
                                                }
                                                if (linear_dayi.getChildCount() == 2) {
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }

                            }else {
                                //隐藏名师答疑
                                constraint.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

    private void getLiveData() {
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeLive, map, true,
                new NovateUtils.setRequestReturn<HomeLiveBean>() {
                    @Override
                    public void onError(Throwable throwable) {
//                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeLiveBean homeLiveBean) {
                        if (homeLiveBean != null) {
                            List<HomeLiveBean.DataBean> data = homeLiveBean.getData();
                            if (data != null && data.size() > 1) {
                                //直播名字
                                if (!TextUtils.isEmpty(data.get(0).getName())) {
                                    tv_title.setText(data.get(0).getName());
                                }
                                if (!TextUtils.isEmpty(data.get(1).getName())) {
                                    tv_title1.setText(data.get(1).getName());
                                }
                                //开始时间
                                if (!TextUtils.isEmpty(data.get(0).getLiveTime())) {
                                    String s = "时间：" + data.get(0).getLiveTime();
                                    tv_time.setText(s);
                                }
                                if (!TextUtils.isEmpty(data.get(1).getLiveTime())) {
                                    String s = "时间：" + data.get(1).getLiveTime();
                                    tv_time1.setText(s);
                                }
                                if (!TextUtils.isEmpty(data.get(0).getTeacherHeaderPath())) {
                                    Glide.with(getActivity()).load(data.get(0).getTeacherHeaderPath()).into((ImageView) iv_header);
                                }
                                if (!!TextUtils.isEmpty(data.get(1).getTeacherHeaderPath())) {
                                    Glide.with(getActivity()).load(data.get(1).getTeacherHeaderPath()).into((ImageView) iv_header1);
                                }
                                //作者
                                if (!TextUtils.isEmpty(data.get(0).getTeacherName())) {
                                    tv_name.setText(data.get(0).getTeacherName());
                                }
                                if (!TextUtils.isEmpty(data.get(1).getTeacherName())) {
                                    tv_name1.setText(data.get(1).getTeacherName());
                                }
                                if (data.get(0) != null) {
                                    liveMould0 = data.get(0).getId();
                                }
                                if (data.get(1) != null) {
                                    liveMould1 = data.get(1).getId();
                                }
                                price0 = data.get(0).getPrice();
                                if (data.get(0).getIsFree() == 0) {
                                    tv_pirce.setText("免费");
                                } else {
                                    tv_pirce.setText("¥" + price0);
                                }

                                price1 = data.get(1).getPrice();
                                if (data.get(1).getIsFree() == 0) {
                                    tv_pirce1.setText("免费");
                                } else {
                                    tv_pirce1.setText("¥" + price1);
                                }
                                if (data.get(0).getType()==1){//1、进入 2、预约 3、购买 4、待开播 5、回放
                                    tv_botton.setText("进入");
                                }else if (data.get(0).getType()==2){
                                    tv_botton.setText("预约");
                                }else if (data.get(0).getType()==3){
                                    tv_botton.setText("购买");
                                }else if (data.get(0).getType()==4){
                                    tv_botton.setText("待开播");
                                }else if (data.get(0).getType()==5){
                                    tv_botton.setText("回放");
                                }
                                if (data.get(1).getType()==1){//1、进入 2、预约 3、购买 4、待开播 5、回放
                                    tv_botton1.setText("进入");
                                }else if (data.get(1).getType()==2){
                                    tv_botton1.setText("预约");
                                }else if (data.get(1).getType()==3){
                                    tv_botton1.setText("购买");
                                }else if (data.get(1).getType()==4){
                                    tv_botton1.setText("待开播");
                                }else if (data.get(1).getType()==5){
                                    tv_botton1.setText("回放");
                                }
                                    //课程类型
                                if (!TextUtils.isEmpty(data.get(0).getTypeName())) {
                                    tv_type.setText(data.get(0).getTypeName());
                                } else {
                                    tv_type.setVisibility(View.GONE);
                                }
                                if (!TextUtils.isEmpty(data.get(1).getTypeName())) {
                                    tv_type1.setText(data.get(1).getTypeName());
                                } else {
                                    tv_type1.setVisibility(View.GONE);
                                }

                            }
                        }
                    }
                });
    }

    /**
     * 获取banner
     */
    private void getBannerData() {

        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeBanner, map, true,
                new NovateUtils.setRequestReturn<HomeBannerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeBannerBean homeBannerBean) {
                        if (homeBannerBean != null) {
                            List<HomeBannerBean.DataBean> data = homeBannerBean.getData();
                            if (data != null && data.size() > 0) {
                                xBanner.removeAllViews();
                                xBanner.setVisibility(View.VISIBLE);
                                initXBanner(data);
                            } else {
                                xBanner.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

    /**
     * 获取金刚区
     */
    private void getKingData() {

        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeKing, map, true,
                new NovateUtils.setRequestReturn<HomeKingBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeKingBean homeKingBean) {
                        if (homeKingBean != null) {
                            kingData = homeKingBean.getData();
                            if (kingData != null && kingData.size() > 0) {
                                pageView.removeAllPages();
                                if (kingData.size() > 9) {
                                    rely_view.setVisibility(View.VISIBLE);
                                    LinearLayout view1 = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_home_page1, null);
                                    recyclerView = view1.findViewById(R.id.recyclerView);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                    pageView.addPage(view1);
                                    LinearLayout view2 = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_home_page1, null);
                                    recyclerView1 = view2.findViewById(R.id.recyclerView);
                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 5);
                                    recyclerView1.setLayoutManager(gridLayoutManager1);
                                    pageView.addPage(view2);
                                    List<HomeKingBean.DataBean> dataBeans = kingData.subList(0, 10);
                                    List<HomeKingBean.DataBean> dataBeans1 = kingData.subList(10, kingData.size());
                                    kingAdapter = new HomeKingAdapter(R.layout.item_home_king, dataBeans);
                                    recyclerView.setAdapter(kingAdapter);
                                    kingAdapter1 = new HomeKingAdapter(R.layout.item_home_king, dataBeans1);
                                    recyclerView1.setAdapter(kingAdapter1);
                                    setItemData(dataBeans, dataBeans1);
                                } else {
                                    rely_view.setVisibility(View.INVISIBLE);
                                    LinearLayout view1 = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_home_page1, null);
                                    recyclerView = view1.findViewById(R.id.recyclerView);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                    kingAdapter = new HomeKingAdapter(R.layout.item_home_king, kingData);
                                    recyclerView.setAdapter(kingAdapter);
                                    pageView.addPage(view1);
                                    setItemData(kingData, null);
                                }


                            }
                        }
                    }
                });

    }

    private void setItemData(List<HomeKingBean.DataBean> dataBeans, List<HomeKingBean.DataBean> dataBeans1) {
        if (dataBeans != null && kingAdapter != null) {
            kingAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    //金刚区跳转
                    //SortHomeActivity
                    Intent intent = new Intent(getActivity(), SortHomeActivity.class);
                    intent.putExtra("title", dataBeans.get(position).getName());
                    intent.putExtra("id", dataBeans.get(position).getId()+"");
                    startActivity(intent);
                }
            });
        }
        if (dataBeans1 != null && kingAdapter1 != null) {
            kingAdapter1.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    //金刚区跳转
                    Intent intent = new Intent(getActivity(), SortHomeActivity.class);
                    intent.putExtra("title", dataBeans1.get(position).getName());
                    intent.putExtra("id", dataBeans1.get(position).getId()+"");
                    startActivity(intent);
                }
            });
        }

    }

    private void initXBanner(List<HomeBannerBean.DataBean> list) {
        // xBanner.removeAllViews();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HomeBannerBean.DataBean dataBean = list.get(i);
            String imgPath = dataBean.getImgPath();
            strings.add(imgPath);
        }
        xBanner.setData(strings, null);
        // XBanner适配数据
        xBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getActivity()).load(strings.get(position)).into((ImageView) view);
            }
        });
        xBanner.setPageTransformer(Transformer.Default);
        xBanner.setPageChangeDuration(1000);
        xBanner.startAutoPlay();
//xbanner的点击事件
        xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(getActivity(), "点击了第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (xBanner != null) {
            xBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (xBanner != null) {
            xBanner.stopAutoPlay();
        }
    }

    @Override
    public void select(int position) {
        if (position == 0) {
            view_tab0.setBackgroundResource(R.drawable.circle_rectangle_c8d3ff_2);
            view_tab1.setBackgroundResource(R.drawable.circle_rectangle_eff1f7_2);

        } else {
            view_tab1.setBackgroundResource(R.drawable.circle_rectangle_c8d3ff_2);
            view_tab0.setBackgroundResource(R.drawable.circle_rectangle_eff1f7_2);
        }
    }


    @OnClick({R.id.homeSearchTv, R.id.rely_xuanke, R.id.rely_live, R.id.rely_wenda, R.id.rely_tiku, R.id.tv_more,
            R.id.tv_botton, R.id.tv_botton1, R.id.tv_dayi_more, R.id.iv_message,R.id.iv_logo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeSearchTv:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("from", "home");
                startActivity(intent);
                break;
            case R.id.rely_xuanke:
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.showFragment(1);
                }
                //


                break;
            case R.id.rely_live:
                startActivity(new Intent(mContext, LiveList_2Activity.class));
                ToastUtils.Toast(getActivity(), "直播");

                break;
            case R.id.rely_wenda:
            case R.id.tv_dayi_more://名师答疑更多
                Intent intent2 = new Intent(getActivity(), WenDaActivity.class);
                startActivity(intent2);

                break;
            case R.id.rely_tiku:
                ToastUtils.Toast(getActivity(), "题库");

                break;
            case R.id.tv_more:
                startActivity(new Intent(mContext, LiveList_2Activity.class));
                ToastUtils.Toast(getActivity(), "直播更多");

                break;
            case R.id.iv_message:
                ToastUtils.Toast(getActivity(), "消息");

                break;
            case R.id.tv_botton:
                if (tv_botton.getText().equals("购买")) {
                    Intent intent1 = new Intent(getActivity(), OrderLiveActivity.class);
                    intent1.putExtra("liveId", liveMould0);
                    startActivity(intent1);
                }else  if (tv_botton.getText().equals("预约")) {
                    showDialog(liveMould0);
                }
                break;
            case R.id.tv_botton1:
                if (tv_botton1.getText().equals("购买")) {
                    Intent intent1 = new Intent(getActivity(), OrderLiveActivity.class);
                    intent1.putExtra("liveId", liveMould1);
                    startActivity(intent1);
                }else  if (tv_botton1.getText().equals("预约")) {
                    showDialog(liveMould1);
                }
                break;
            case R.id.iv_logo:
                break;
        }
    }
    public void showDialog(int liveId){
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());  //先得到构造器
            builder.setTitle("提示"); //设置标题
            builder.setMessage("是否确认预约?"); //设置内容
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); //关闭dialog
                    //预约
                    reserve(liveId);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.create().show();

    }
    public void reserve(int liveId){
        HashMap<String, String> map = new HashMap<>();
        map.put("liveId",liveId+"");
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeReserve, map, true,
                new NovateUtils.setRequestReturn<HomeBannerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeBannerBean homeBannerBean) {
                        if (homeBannerBean != null) {
                            getLiveData();
                        }
                    }
                });
    }
}
