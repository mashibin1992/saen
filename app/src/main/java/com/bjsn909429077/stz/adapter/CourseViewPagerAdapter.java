package com.bjsn909429077.stz.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class CourseViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<String> tabTitle;
    private final ArrayList<Fragment> fragments;

    public CourseViewPagerAdapter(ArrayList<String> tabTitle, ArrayList<Fragment> fragments, FragmentManager supportFragmentManager) {
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
        return tabTitle.get(position);
    }

}
