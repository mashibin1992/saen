package com.bjsn909429077.stz.ui.course;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.home.SearchActivity;
import com.bjsn909429077.stz.ui.home.SortActivity2;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import butterknife.BindView;


public class CourseSelectionFragment extends BaseLazyLoadFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.sort_tb_title)
    TextView sort_tb_title;
    @BindView(R.id.fragment_vp)
    ViewPager fragment_vp;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.sort_top_search)
    ImageView sort_top_search;
    public int secondTypeId = 0;
    private int position;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public int firstTypeId = -1;
    private String tabtitle;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_course_selection;
    }

    @Override
    protected void init() {
        initViewPager();
        initListener();
    }

    private void initListener() {
        tool_bar.setNavigationOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();
                activity.showFragment(0);
            }
        });
        sort_top_search.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("from", "kecheng");
            startActivity(intent);
        });
        sort_tb_title.setOnClickListener(v -> {
            startActivityForResult(new Intent(mContext, SortActivity2.class), Constant.REQUEST_CODE);
        });
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            firstTypeId = (int) SharedPreferencesUtil.getData(getActivity(), "firstTypeId", -1);
            tabtitle = (String) SharedPreferencesUtil.getData(getActivity(), "title", "");
            sort_tb_title.setText(tabtitle);
            if (-1 == firstTypeId) {
                startActivity(new Intent(getActivity(), SortActivity2.class));
            } else {
                SelectCourseListFragment selectCourseListFragment = (SelectCourseListFragment) fragments.get(position);
                selectCourseListFragment.loadData2();
            }
        }
    }


    private void initViewPager() {

        title.add("全部");
        title.add("付费课程");
        title.add("免费课程");
        for (int i = 0; i < title.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("firstTypeId", firstTypeId);
            bundle.putInt("type", i);
            fragments.add(SelectCourseListFragment.getInstance(bundle));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                SelectCourseListFragment selectCourseListFragment = (SelectCourseListFragment) fragments.get(tab.getPosition());
                selectCourseListFragment.refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        CourseViewPagerAdapter courseViewPagerAdapter = new CourseViewPagerAdapter(title, fragments, getChildFragmentManager());
        fragment_vp.setAdapter(courseViewPagerAdapter);
        fragment_vp.setOffscreenPageLimit(title.size());
        tabLayout.setupWithViewPager(fragment_vp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //筛选
        if (requestCode == Constant.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String title = data.getStringExtra("title");
                sort_tb_title.setText(title);
                firstTypeId = data.getIntExtra("id", 0);
                SelectCourseListFragment selectCourseListFragment = (SelectCourseListFragment) fragments.get(position);
                selectCourseListFragment.loadData2();
            }
        }
    }
}
