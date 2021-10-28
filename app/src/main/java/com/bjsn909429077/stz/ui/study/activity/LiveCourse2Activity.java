package com.bjsn909429077.stz.ui.study.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.adapter.LiveViewPagerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.LiveTabBean;
import com.bjsn909429077.stz.ui.study.fragment.LiveCourseFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LiveCourse2Activity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private int coursePackageId;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_live_course2;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle(R.string.live_course_title);
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            coursePackageId = getIntent().getIntExtra("coursePackageId", 0);
        }
    }

    @Override
    protected void initData() {
        initTabData();
    }

    private void initTabData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.tabList, map, true,
                new NovateUtils.setRequestReturn<LiveTabBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(LiveTabBean response) {
                        if (response != null && response.data != null) {
                            showTwoPage(response.data);
                        }
                    }
                });
    }


    public void showTwoPage(List<LiveTabBean.DataBean> data) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<LiveTabBean.DataBean> strings = new ArrayList<>();
//        strings.add("全部");
//        strings.add("二级分类");
//        strings.add("中级考试");
//        strings.add("考注会");
        fragments.clear();
        for (int i = 0; i < data.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("typeId", data.get(i).id);
            bundle.putInt("coursePackageId", coursePackageId);
            fragments.add(LiveCourseFragment.getInstance(bundle));
        }
        LiveViewPagerAdapter courseViewPagerAdapter = new LiveViewPagerAdapter(strings, fragments, getSupportFragmentManager());
        view_pager.setAdapter(courseViewPagerAdapter);
        view_pager.setOffscreenPageLimit(strings.size());
        tab_layout.setupWithViewPager(view_pager);
    }

}