package com.bjsn909429077.stz.ui.study;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.bean.HasBuyListBean;
import com.bjsn909429077.stz.bean.RecentBrowesBean;
import com.bjsn909429077.stz.bean.StudyCenterHomeBean;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.home.SortActivity2;
import com.bjsn909429077.stz.ui.study.activity.AnswerListActivity;
import com.bjsn909429077.stz.ui.study.activity.MyCourseListActivity;
import com.bjsn909429077.stz.ui.study.activity.MyLiveActivity;
import com.bjsn909429077.stz.ui.study.activity.MyNoteListActivity;
import com.bjsn909429077.stz.ui.study.fragment.BrowseFragment;
import com.bjsn909429077.stz.ui.study.fragment.PurchaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;


public class StudyFragment extends BaseLazyLoadFragment {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.sort_tb_title)
    TextView sortTbTitle;
    @BindView(R.id.tv_wrong)
    TextView tv_wrong;
    @BindView(R.id.tv_answer)
    TextView tv_answer;
    @BindView(R.id.tv_hear)
    TextView tv_hear;
    @BindView(R.id.my_course_count)
    TextView my_course_count;
    @BindView(R.id.my_dayi)
    TextView my_dayi;
    @BindView(R.id.my_live_count)
    TextView my_live_count;
    @BindView(R.id.my_note_count)
    TextView my_note_count;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.ll_screen)
    LinearLayout ll_screen;
    //最近浏览页码
    private Integer page = 0;
    public int secondTypeId = 0;
    private int firstTypeId;
    private String tabtitle;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_study;
    }


    @Override
    protected void init() {
        initViewPager();
        initListener();
    }

    private void initListener() {
        toolBar.setNavigationOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();
                activity.showFragment(0);
            }
        });
    }

    @Override
    protected void loadData() {
        HashMap<String, Integer> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.studyHome, map, true,
                new NovateUtils.setRequestReturn<StudyCenterHomeBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(StudyCenterHomeBean response) {
                        if (response != null && response.data != null) {
                            StudyCenterHomeBean.DataBean data = response.data;
                            tv_title.setText("Hi～，您已经连续学习" + data.continuityStudyDays + "天啦！");
                            tv_wrong.setText(data.answerWrongCount + "");
                            tv_answer.setText(data.answerCount + "");
                            tv_hear.setText(data.studyCourseLength / 60 + "");
                            my_course_count.setText(data.mineCourseCount + "");
                            my_live_count.setText(data.mineLiveCount + "");
                            my_dayi.setText(data.courseDaYiCount + "");
                            my_note_count.setText(data.courseNoteCount + "");
                        }
                    }
                });
//        yiGou(0);
//        zuiJinLiuLan(0);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            firstTypeId = (int) SharedPreferencesUtil.getData(getActivity(), "firstTypeId", -1);
            tabtitle = (String) SharedPreferencesUtil.getData(getActivity(), "title", "");
            if (-1 == firstTypeId) {
                startActivity(new Intent(getActivity(), SortActivity2.class));
            } else {
                zuiJinLiuLan(firstTypeId);
                yiGou(firstTypeId);
            }
        }
    }


    private void zuiJinLiuLan(int typeId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("typeId", typeId);
        map.put("page", page);
        NovateUtils.getInstance().Post(mContext, BaseUrl.recentBrowse, map, true,
                new NovateUtils.setRequestReturn<RecentBrowesBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(RecentBrowesBean response) {
                        if (response != null && response.data != null) {
                            RxBus.getDefault().postSticky(new EventBean<>(2, response.data));
                        }
                    }
                });
    }

    private void yiGou(int typeId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("typeId", typeId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.hasBuyList, map, true,
                new NovateUtils.setRequestReturn<HasBuyListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(HasBuyListBean response) {
                        Log.d("af", "onSuccee: " + "成功");
                        if (response != null && response.data != null) {
                            RxBus.getDefault().postSticky(new EventBean<>(EventBean.PURCHASED_COURSE, response.data));
                        }
                    }
                });
    }

    private void initViewPager() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("已购课程");
        strings.add("最近浏览");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PurchaseFragment());
        fragments.add(new BrowseFragment());
        CourseViewPagerAdapter courseViewPagerAdapter = new CourseViewPagerAdapter(strings, fragments, getChildFragmentManager());
        viewpager.setAdapter(courseViewPagerAdapter);
        tablayout.setupWithViewPager(viewpager);
        StudyFragmentConfig.bigOrSmall(strings, tablayout, mContext);


    }

    @OnClick({R.id.ll_course, R.id.ll_live, R.id.ll_answer, R.id.ll_note, R.id.ll_screen})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_course:

                startActivity(new Intent(getActivity(), MyCourseListActivity.class));
                break;
            case R.id.ll_live:
                startActivity(new Intent(getActivity(), MyLiveActivity.class));
                break;
            case R.id.ll_answer:
                startActivity(new Intent(getActivity(), AnswerListActivity.class));
                break;
            case R.id.ll_note:
                startActivity(new Intent(getActivity(), MyNoteListActivity.class));
                break;
            case R.id.ll_screen:
                startActivityForResult(new Intent(mContext, SortActivity2.class), Constant.REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //筛选
        if (requestCode == Constant.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra("title");
            secondTypeId = data.getIntExtra("id", 0);
            page = 0;
            yiGou(secondTypeId);
            zuiJinLiuLan(secondTypeId);

        }
    }
}
