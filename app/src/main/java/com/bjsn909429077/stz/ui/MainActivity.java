package com.bjsn909429077.stz.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ShowFragment;
import com.bjsn909429077.stz.ui.course.CourseSelectionFragment;
import com.bjsn909429077.stz.ui.home.HomeFragment;
import com.bjsn909429077.stz.ui.login.LoginActivity;
import com.bjsn909429077.stz.ui.my.MyFragment;
import com.bjsn909429077.stz.ui.questionbank.QuestionBankFragment;
import com.bjsn909429077.stz.ui.study.StudyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jiangjun.library.ui.adapter.FragAdapter;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    @BindView(R.id.home_viewpager)
    NoScrollViewPager viewPager;
    @BindView(R.id.bnv_view)
    BottomNavigationView bnvView;
    private ArrayList<Fragment> fragments;
    private long exitToastTime = 0;

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        if (!RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().register(this);
        }
        initRxBus();

        view_head.setVisibility(View.GONE);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CourseSelectionFragment());
        fragments.add(new StudyFragment());
        fragments.add(new QuestionBankFragment());
        fragments.add(new MyFragment());

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());

        bnvView.setItemIconTintList(null);
        bnvView.setOnNavigationItemSelectedListener(item -> {
            int res = item.getItemId();
            switch (res) {

                case R.id.homeFragment:
                    getBut(0);
                    break;
                case R.id.communityFragment:
                    getBut(1);
                    break;
                case R.id.mallFragment:
                    getBut(2);
                    break;
                case R.id.idleFragment:
                    getBut(3);
                    break;
                case R.id.myFragment:

                    getBut(4);
                    break;

            }

            return true;
        });

        getBut(0);
    }

    @Override
    protected void initData() {

    }


    public void getBut(int i) {
        viewPager.setCurrentItem(i);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RangerEvent.RefreshData event) {
        if (event.type.equals("1")) {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().unregister(this);
        }
    }


    @Override
    public void onBackPressed() {
        if (exitToastTime != 0 && System.currentTimeMillis() - exitToastTime < 1800) {
            AppManager.getAppManager().AppExit(this);
            super.onBackPressed();
        } else {
            exitToastTime = System.currentTimeMillis();
            ToastUtils.Toast(mContext, "再按一次退出程序");
        }
    }

    public void showFragment(int position) {
        getBut(position);
        bnvView.setSelectedItemId(bnvView.getMenu().getItem(position).getItemId());
    }

    @SuppressLint("CheckResult")
    private void initRxBus() {
        Disposable subscriptionMediaViewPagerChangedEvent = RxBus.getDefault().toObservableSticky(ShowFragment.class)
                .subscribeWith(new RxBusDisposable<ShowFragment>() {
                    @Override
                    protected void onEvent(ShowFragment eventBean) {
                        getBut(eventBean.getTYPE());
                        bnvView.setSelectedItemId(bnvView.getMenu().getItem(eventBean.getTYPE()).getItemId());
                    }
                });
        RxBus.getDefault().add(subscriptionMediaViewPagerChangedEvent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
