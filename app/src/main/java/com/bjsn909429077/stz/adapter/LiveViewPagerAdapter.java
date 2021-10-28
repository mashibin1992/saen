package com.bjsn909429077.stz.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bjsn909429077.stz.bean.LiveTabBean;

import java.util.ArrayList;

public class LiveViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<LiveTabBean.DataBean> tabTitle;
    private final ArrayList<Fragment> fragments;

    public LiveViewPagerAdapter(ArrayList<LiveTabBean.DataBean> tabTitle, ArrayList<Fragment> fragments, FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        this.tabTitle = tabTitle;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position).name;
    }
}
