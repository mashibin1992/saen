package com.bjsn909429077.stz.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomePageFragmentAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.GuideInfoBean;
import com.bjsn909429077.stz.bean.HomeBannerBean2;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.bean.HomeRecommendtypeBean;
import com.bjsn909429077.stz.bean.ShowFragment;
import com.bjsn909429077.stz.ui.course.SelectCourseActivity;
import com.bjsn909429077.stz.ui.home.contract.SortHomeContract;
import com.bjsn909429077.stz.ui.home.model.SortHomeModel;
import com.bumptech.glide.Glide;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.StatusBarUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;
import com.stx.xhb.xbanner.XBanner;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;

public class SortHomeActivity extends BaseActivity implements SortHomeContract {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.sort_tb_title)
    TextView sort_tb_title;
    @BindView(R.id.sort_date_hundred)
    TextView sort_date_hundred; //百
    @BindView(R.id.sort_date_ten)
    TextView sort_date_ten;     //十
    @BindView(R.id.sort_date_individual)
    TextView sort_date_individual; //个
    @BindView(R.id.sort_date_text_year)
    TextView sort_date_text_year; //年
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.rl_chapter)
    RelativeLayout rl_chapter;
    @BindView(R.id.rl_special)
    RelativeLayout rl_special;
    @BindView(R.id.rl_over_the_years)
    RelativeLayout rl_over_the_years;
    @BindView(R.id.rl_simulation)
    RelativeLayout rl_simulation;
    @BindView(R.id.rl_before)
    RelativeLayout rl_before;
    @BindView(R.id.ll_guide)
    LinearLayout ll_guide;
    @BindView(R.id.sort_top_search)
    ImageView sort_top_search;
    @BindView(R.id.sort_banner)
    XBanner sort_banner;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.linear_dayi)
    LinearLayout linear_dayi;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;    @BindView(R.id.rely_kecheng)
    RelativeLayout rely_kecheng;@BindView(R.id.constraint)
    ConstraintLayout constraint;
    private List<String> bannerImages = new ArrayList<>();
    private List<HomeRecommendListBean.DataBean> data = new ArrayList<>();
    private SortHomeModel sortHomeModel;
    private List<HomeBannerBean2.DataDTO> bannerList;
    private String id;
    private HomePageFragmentAdapter mHomePageFragmentAdapter;
    private int mFirstTypeId;
    private boolean isFirst = true;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_sort_home;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setHeadHighly(mContext, view_head); //设置状态栏高度
        sortHomeModel = new SortHomeModel(mContext, this);
        mFirstTypeId = (int) SharedPreferencesUtil.getData(mContext, "one", -1);
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            String title = getIntent().getStringExtra("title");
            id = getIntent().getStringExtra("id");
            sort_tb_title.setText(title);
        }

        HashMap<String, String> map = new HashMap<>();
        sortHomeModel.getBannerData(map);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("firstTypeId", id);
        sortHomeModel.getListData(map2);

        getGuideInfo(id);
        initCourseRecycler();
        getRecommendList();
        initListener();
    }

    private void getGuideInfo(Object id) {
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("firstTypeId", id);
        sortHomeModel.getInfoGuide(map2);
    }

    private void initCourseRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mHomePageFragmentAdapter = new HomePageFragmentAdapter(R.layout.item_home_remmcond, data, SortHomeActivity.this);
        recyclerView.setAdapter(mHomePageFragmentAdapter);
    }
//
//    private void getListData() {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("firstTypeId", id);
//        NovateUtils.getInstance().Post(mContext, BaseUrl.homeRecommendList, map, true,
//                new NovateUtils.setRequestReturn<HomeRecommendListBean>() {
//                    @Override
//                    public void onError(Throwable throwable) {
//                        ToastUtils.Toast(mContext, throwable.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccee(HomeRecommendListBean homeRecommendListBean) {
//                        if (homeRecommendListBean != null) {
//                            if (homeRecommendListBean.getData() != null && homeRecommendListBean.getData().size() > 0) {
//                                List<HomeRecommendListBean.DataBean> data2 = homeRecommendListBean.getData();
//                                if (data2.size() > 3) {
//                                    data2 = data2.subList(0, 3);
//                                    data.addAll(data2);
//                                }
//                                mHomePageFragmentAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });
//
//    }


    @Override
    public void getListData(List<HomeRecommendListBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            rely_kecheng.setVisibility(View.VISIBLE);
            if (data.size() > 3) {
                this.data.addAll(data.subList(0, 3));
            }
            mHomePageFragmentAdapter.notifyDataSetChanged();
        }else {
            rely_kecheng.setVisibility(View.GONE);
        }
    }


    //获取banner列表2
    @Override
    public void getHomeBanner(HomeBannerBean2 homeBannerBean) {
        bannerList = homeBannerBean.data;
        for (int i = 0; i < bannerList.size(); i++) {
            bannerImages.add(bannerList.get(i).imgPath);
        }
        initBanner();
    }

    @Override
    public void getInfoGuide(GuideInfoBean.DataBean response) {
        String data="";
        if (!TextUtil.isEmpty(response.fromExaminationYear)){
            data= response.fromExaminationYear;
        }
        sort_date_text_year.setText(data + "年考试倒计时");
        if (response.fromExaminationDay != null) {
            int length = response.fromExaminationDay.length();
            switch (length) {
                case 1:
                    sort_date_hundred.setVisibility(View.GONE);
                    sort_date_ten.setVisibility(View.GONE);
                    sort_date_individual.setText(response.fromExaminationDay);
                    break;
                case 2:
                    sort_date_hundred.setVisibility(View.GONE);
                    sort_date_ten.setText(response.fromExaminationDay.charAt(1));
                    sort_date_individual.setText(response.fromExaminationDay.charAt(0));
                    break;
                case 3:
                    sort_date_individual.setText(response.fromExaminationDay.charAt(0));
                    sort_date_ten.setText(response.fromExaminationDay.charAt(1));
                    sort_date_hundred.setText(response.fromExaminationDay.charAt(2));
                    break;
            }
        }

    }

    private void initBanner() {
        sort_banner.setData(bannerImages, null);
        sort_banner.setmAdapter((banner, view, position) -> {
            Glide.with(mContext).load(bannerImages.get(position)).into((ImageView) view);
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
                                                Glide.with(SortHomeActivity.this).load(answerPics.get(0)).into(iv_1);
                                            }
                                            if (answerPics.size() == 2 && !TextUtils.isEmpty(answerPics.get(1))) {
                                                Glide.with(SortHomeActivity.this).load(answerPics.get(1)).into(iv_2);
                                            } else {
                                                iv_2.setVisibility(View.INVISIBLE);
                                                iv_3.setVisibility(View.INVISIBLE);
                                            }
                                            if (answerPics.size() == 3 && !TextUtils.isEmpty(answerPics.get(2))) {
                                                Glide.with(SortHomeActivity.this).load(answerPics.get(2)).into(iv_3);
                                            } else {
                                                iv_3.setVisibility(View.INVISIBLE);
                                            }
                                        }

                                        int finalI = i;
                                        linear_1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ToastUtils.Toast(SortHomeActivity.this, "名师答疑" + data.get(finalI).getQuestionName());
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
                                                            ToastUtils.Toast(SortHomeActivity.this, "名师答疑" + data.get(finalI1).getQuestionName());
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
                                constraint.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }


    private void initListener() {
        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });

        sort_tb_title.setOnClickListener(v -> {//一级级筛选
            startActivityForResult(new Intent(mContext, SortActivity.class), Constant.REQUEST_CODE);
        });

        sort_top_search.setOnClickListener(v -> {//搜索
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("from", "kecheng");
            startActivity(intent);
        });

        tv_more.setOnClickListener(v -> {   //更多
            showHomeFragmentPage(ShowFragment.QUESTION);
        });

        ll_guide.setOnClickListener(v -> {   //公告
            Intent intent = new Intent(this, SortGuideActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        mHomePageFragmentAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, SelectCourseActivity.class);
            intent.putExtra("coursePackageId", data.get(position).getCoursePackageId());
            startActivity(intent);
        });
    }

    //章节
    public void rl_chapter(View view) {
        showHomeFragmentPage(ShowFragment.QUESTION);
    }

    //专项
    public void rl_special(View view) {
        showHomeFragmentPage(ShowFragment.QUESTION);
    }

    //历年
    public void rl_over_the_years(View view) {
        showHomeFragmentPage(ShowFragment.QUESTION);
    }

    //模拟
    public void rl_simulation(View view) {
        showHomeFragmentPage(ShowFragment.QUESTION);
    }

    //考前
    public void rl_before(View view) {
        showHomeFragmentPage(ShowFragment.QUESTION);
    }

    private void showHomeFragmentPage(int position) {
        RxBus.getDefault().post(new ShowFragment(position));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //筛选
        if (requestCode == Constant.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra("title");
            sort_tb_title.setText(title);
            getGuideInfo(data.getIntExtra("id", 0));
        }
    }

    @OnClick({R.id.tv_dayi_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dayi_more:
                ToastUtils.Toast(SortHomeActivity.this, "名师答疑更多");

                break;

        }
    }
}