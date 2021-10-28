package com.bjsn909429077.stz.ui.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.api.Const;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.ui.course.contract.SelectCourseActivityContract;
import com.bjsn909429077.stz.ui.course.model.SelectCourseActivityModel;
import com.bjsn909429077.stz.ui.home.SearchActivity;
import com.bjsn909429077.stz.ui.login.LoginActivity;
import com.bjsn909429077.stz.ui.order.PaymentActivity;
import com.bjsn909429077.stz.ui.study.activity.ContinueStudyActivity;
import com.bjsn909429077.stz.utils.TakePhotoPopWin;
import com.blankj.utilcode.util.MessengerUtils;
import com.bumptech.glide.Glide;
import com.example.weblibrary.Bean.WebProphetMessage;
import com.example.weblibrary.CallBack.ChatActivityCallback;
import com.example.weblibrary.Manager.VP53Manager;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;

public class SelectCourseActivity extends BaseActivity implements SelectCourseActivityContract, TakePhotoPopWin.OnCouponItemCallback , MessengerUtils.MessageCallback {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.sort_top_search)
    ImageView sort_top_search;
    @BindView(R.id.fragment_vp)
    ViewPager fragment_vp;
    @BindView(R.id.iv_header)
    ImageView iv_header;//头部图片
    @BindView(R.id.tv_title_s_course)
    TextView tv_title_s_course;//标题
    @BindView(R.id.tv_sub_title)
    TextView tv_sub_title;//二级标题
    @BindView(R.id.tv_money)
    TextView tv_money;//价格
    @BindView(R.id.tv_mode)
    TextView tv_mode;//付款方式
    @BindView(R.id.image_icon)
    ImageView image_icon;//赠品图标
    //年月日
    @BindView(R.id.tv_date_1)
    TextView tv_date_1;
    //月
    @BindView(R.id.tv_date_2)
    TextView tv_date_2;
    //课程1
    @BindView(R.id.tv_courser_1)
    TextView tv_courser_1;
    //课程1
    @BindView(R.id.tv_courser_2)
    TextView tv_courser_2;
    //管家
    @BindView(R.id.tv_housekeeper)
    TextView tv_housekeeper;
    //答疑
    @BindView(R.id.tv_answer)
    TextView tv_answer;
    //保价
    @BindView(R.id.tv_insured_price)
    TextView tv_insured_price;
    //优惠
    @BindView(R.id.tv_discount)
    TextView tv_discount;

    @BindView(R.id.tv_yang)
    TextView tv_yang;
    //报名
    @BindView(R.id.btn_baoming)
    TextView btn_baoming;
    @BindView(R.id.tv_original_price)
    TextView tv_original_price;
    //线
    @BindView(R.id.v_line)
    View v_line;
    //立即领取
    @BindView(R.id.ll_receive)
    LinearLayout ll_receive;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.ll_effective)
    LinearLayout ll_effective;
    //特色
    @BindView(R.id.ll_style)
    LinearLayout ll_style;
    @BindView(R.id.ll_course)
    LinearLayout ll_course;
//    @BindView(R.id.sll_center)
//    NestedScrollView sll_center;

    private CourseViewPagerAdapter mSelectCourseViewPagerAdapter;
    private Integer coursePackageId = 0;
    private SelectCourseActivityModel mSelectCourseActivityModel;
    private ArrayList<String> mTabTitle = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean> courseChapersBean = new ArrayList<>();
    private CourseArrangeFragment courseArrangeFragment;
    private Integer coursePackagePriceId = 0;
    private List<Object> mStrings2;
    private ArrayList<CoursePackageInfoBean.DataBean.CoursePackagePriceListBean> mCoursePackagePriceListBeans = new ArrayList<>();
    private TakePhotoPopWin mTakePhotoPopWin;
    private boolean isLoadFinish;
    private CoursePackageInfoBean.DataBean mDataBean;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_select_course;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            coursePackageId = intent.getIntExtra("coursePackageId", 0);
        }
        mSelectCourseActivityModel = new SelectCourseActivityModel(mContext, this);
        mTakePhotoPopWin = new TakePhotoPopWin(mContext);
        mTakePhotoPopWin.setOnCouponItemCallback(this);
        tv_date_2.setVisibility(View.GONE);
        tv_date_1.setVisibility(View.GONE);
        initFragment();

        initScrollListener();

    }

    private void initScrollListener() {

    }

    @Override
    protected void initData() {
        Map<String, Integer> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);
        Log.d("详情参数", "loadData: " + map.toString());
        mSelectCourseActivityModel.getCoursePackageInfo(map);
    }

    @SuppressLint("CommitTransaction")
    private void initFragment() {
        mStrings2 = new ArrayList<>();
        initListener();
        mTabTitle.add("课程详情");
        Bundle bundle = new Bundle();
        bundle.putInt("coursePackageId", coursePackageId);
        mFragments.add(CourseInfoFragment.getInstance(bundle));
        courseArrangeFragment = new CourseArrangeFragment();
        mSelectCourseViewPagerAdapter = new CourseViewPagerAdapter(mTabTitle, mFragments, getSupportFragmentManager());
        fragment_vp.setAdapter(mSelectCourseViewPagerAdapter);
        tabLayout.setupWithViewPager(fragment_vp);
    }


    private void initListener() {

        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
        sort_top_search.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("from", "kecheng");
            startActivity(intent);
        });
        tv_date_2.setOnClickListener(v -> {
            selectMonth();
        });
        btn_baoming.setOnClickListener(v -> {
            if (btn_baoming.getText().equals("立即报名")) {
                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra("coursePackagePriceId", coursePackagePriceId);
                intent.putExtra("coursePackageId", coursePackageId);
                startActivity(intent);
            }else if (btn_baoming.getText().equals("去学习")){
                Intent intent = new Intent(this, ContinueStudyActivity.class);
                intent.putExtra("coursePackageId", coursePackageId);
                startActivity(intent);
            }
        });
        ll_receive.setOnClickListener(v -> {
            mTakePhotoPopWin.showAsDropDown(getWindow().getDecorView(), 0, 0, Gravity.BOTTOM);
        });
    }

    /**
     * 有效期
     */
    private void selectMonth() {
        OptionsPickerView<Object> ds = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            if (mCoursePackagePriceListBeans.get(options1).discountPrice != null) {
                tv_money.setText(mCoursePackagePriceListBeans.get(options1).discountPrice + "");
                tv_original_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                tv_original_price.setText("¥" + mCoursePackagePriceListBeans.get(options1).price.toString());
            }
            if (mCoursePackagePriceListBeans.get(options1).effectiveEtime != null) {
                tv_date_2.setVisibility(View.GONE);
                tv_date_1.setVisibility(View.VISIBLE);
                tv_date_1.setText("购买之日至：" + DateUtil.formatYyyyMmDd(mCoursePackagePriceListBeans.get(options1).effectiveEtime));
            }
            if (mCoursePackagePriceListBeans.get(options1).effectiveMonth != null) {
                tv_date_1.setVisibility(View.GONE);
                tv_date_2.setVisibility(View.VISIBLE);
                tv_date_2.setText(mCoursePackagePriceListBeans.get(options1).effectiveMonth.toString() + "个月");
            }
            //套餐价格id
            if (mCoursePackagePriceListBeans.get(options1).coursePackagePriceId != null) {
                coursePackagePriceId = mCoursePackagePriceListBeans.get(options1).coursePackagePriceId;
            }
        }).setTitleText("有效期")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .setCyclic(false, false, false)
                .build();
        ds.setNPicker(mStrings2, null, null);
        ds.show();
    }


    public void customerService(View view) {
        ToastUtils.Toast(mContext, "客服");

        if (UserConfig.isLogin())
            inVP53();
        else
            startActivity(new Intent(mContext, LoginActivity.class));
    }


    private void inVP53(){

        String visitorId =  UserConfig.getString("visitorId", "");
        if (!visitorId.isEmpty()) {
            VP53Manager.getInstance()
                    .getVisitorConfigManager(Const.KF_53_arg, visitorId)

                    .setCustomName(UserConfig.getUser().getNickName())
                    .setPhone(UserConfig.getUser().getMobile())
//                    .setEmail("")
//                    .setQq("")
//                    .setWechat("")
//                    .setNotes("")
//                    .setCompany("")
//                    .setAddress("")
//                    .setCustomId("")
//                    .setCustomInfo("")
                    .apply();

        }

        //这是图文信息
        WebProphetMessage webMessage = new WebProphetMessage();
        webMessage.setImg(mDataBean.coverPath);
        webMessage.setMsg(mDataBean.courseName);
        webMessage.setType("1");

        //这是纯图片信息
        WebProphetMessage webMessage1 = new WebProphetMessage();
        webMessage1.setImg("https://www.53kf.com/main.PNG");
        webMessage1.setType("1");

        //这是纯文本信息
        WebProphetMessage webMessage2 = new WebProphetMessage();
        webMessage2.setMsg("这是预发纯文本信息");
        webMessage2.setType("1");

        List<WebProphetMessage> messages = new ArrayList<>();
                messages.add(webMessage);
//                messages.add(webMessage1);
//                messages.add(webMessage2);

        VP53Manager.getInstance().startChatActivity(Const.KF_53_arg, "1", "",
                mContext, messages, new ChatActivityCallback() {
                    @Override
                    public void onChatActivityFinished() {
                        RLog.e("TAG", "onChatActivityFinished");
                    }
                });
    }

    //优惠券点击
    @SuppressLint("SetTextI18n")
    @Override
    public void couponCallback(CoursePackageInfoBean.DataBean.CourseCouponListBean data) {
        tv_discount.setText("满" + data.enablePrice + "减" + "(" + data.price + ")");
        Map<String, Integer> map = new HashMap<>();
        map.put("courseCouponId", data.courseCouponId);
        //领取优惠券
        mSelectCourseActivityModel.receiveCoupon(map);

    }

    /**
     * 详情数据
     *
     * @param data
     */
    @Override
    public void coursePackageInfo(CoursePackageInfoBean.DataBean data) {
        mDataBean = data;
        if (data.isFree == 0) {
            v_line.setVisibility(View.GONE);
            tv_mode.setVisibility(View.GONE);
            tv_yang.setVisibility(View.GONE);
            ll_style.setVisibility(View.GONE);
            ll_course.setVisibility(View.GONE);
            ll_receive.setVisibility(View.GONE);
            tv_original_price.setVisibility(View.GONE);
            tv_money.setText("免费");
            btn_baoming.setText("去学习");
        }
        //封面
        if (!TextUtils.isEmpty(data.coverPath)) {
            Glide.with(mContext).load(data.coverPath).into(iv_header);
        }
        //标题
        if (data.courseName != null) {
            tv_title_s_course.setText(data.courseName);
            Log.d(TAG, "coursePackageInfo: " + data.courseName);
        }
        if (data.courseSecondName != null) {
            tv_sub_title.setText(data.courseSecondName);
        }
        //有效期
        if (data.coursePackagePriceList != null && data.coursePackagePriceList.size() > 0) {
            List<CoursePackageInfoBean.DataBean.CoursePackagePriceListBean> coursePackagePriceList = data.coursePackagePriceList;
            mCoursePackagePriceListBeans.clear();
            mCoursePackagePriceListBeans.addAll(coursePackagePriceList);
            if (coursePackagePriceList.get(0).price != null) {
                tv_original_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                tv_original_price.setText("¥" + coursePackagePriceList.get(0).price);
            }
            if (coursePackagePriceList.get(0).discountPrice != null) {
                tv_money.setText(data.coursePackagePriceList.get(0).discountPrice + "");
                tv_mode.setText("折扣倒计时:" + DateUtil.formatHHMMSS(data.coursePackagePriceList.get(0).effectiveEtime) + "结束");
            }
            if (coursePackagePriceList.get(0).effectiveEtime != null) {
                tv_date_2.setVisibility(View.GONE);
                tv_date_1.setVisibility(View.VISIBLE);
                tv_date_1.setText("购买之日至：" + DateUtil.formatYyyyMmDd(coursePackagePriceList.get(0).effectiveEtime));
            }
            if (coursePackagePriceList.get(0).effectiveMonth != null) {
                tv_date_1.setVisibility(View.GONE);
                tv_date_2.setVisibility(View.VISIBLE);
                tv_date_2.setText(coursePackagePriceList.get(0).effectiveMonth.toString() + "个月");
            }
            //套餐价格id
            if (coursePackagePriceList.get(0).coursePackagePriceId != null) {
                coursePackagePriceId = coursePackagePriceList.get(0).coursePackagePriceId;
            }
            mStrings2.clear();
            for (int i = 0; i < data.coursePackagePriceList.size(); i++) {
                mStrings2.add(data.coursePackagePriceList.get(i).effectiveMonth + "个月");
            }
        }

        //根据后台返回数据动态添加fragment
        if (data.courseScheduleList != null && data.courseScheduleList.size() > 0) {
            mTabTitle.add("课程安排");
            mFragments.add(courseArrangeFragment);
            RxBus.getDefault().postSticky(new EventBean<>(EventBean.SELECT_COURSE, data.courseScheduleList));
        }
        mSelectCourseViewPagerAdapter.notifyDataSetChanged();
        //赠送课程
        if (data.giveCourseNames != null && data.giveCourseNames.size() > 0) {
            tv_courser_1.setText(data.giveCourseNames.get(0));
            if (data.giveCourseNames.size() >= 2)
                tv_courser_2.setText(data.giveCourseNames.get(1));
            else
                tv_courser_2.setVisibility(View.GONE);
        }
        //特色服务
        if (data.specialServiceNames != null && data.specialServiceNames.size() > 0) {
            tv_housekeeper.setText(data.specialServiceNames.get(0));
            if (data.giveCourseNames.size() >= 2)
                tv_answer.setText(data.specialServiceNames.get(1));
            else
                tv_answer.setVisibility(View.GONE);
            if (data.giveCourseNames.size() >= 3)
                tv_insured_price.setText(data.specialServiceNames.get(2));
            else
                tv_insured_price.setVisibility(View.GONE);
        }
        //优惠券
        if (data.courseCouponList != null && data.courseCouponList.size() > 0) {
            tv_discount.setText("满" + data.courseCouponList.get(0).enablePrice + "减" + "(" + data.courseCouponList.get(0).price + ")");
            if (data.courseCouponList.size() >= 3) {
                mTakePhotoPopWin.setData(data.courseCouponList.subList(0, 2));
            } else {
                mTakePhotoPopWin.setData(data.courseCouponList);
            }
        }
    }

    @Override
    public void receiveCoupon(CoursePackageInfoBean response) {
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void messageCall(Bundle data) {

    }
}