package com.jiangjun.library.ui.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/16.
 */

public class FindAdapter extends FragmentPagerAdapter {
    private List<String> titles=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();


    public FindAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        resetData(titles,fragments);
    }

    public void resetData(List<String> titles, List<Fragment> fragments){
        if(this.titles!=null&&this.fragments!=null){
            this.titles.clear();
            this.titles.addAll(titles);
            this.fragments.clear();
            this.fragments.addAll(fragments);
            this.notifyDataSetChanged();
        }
    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
