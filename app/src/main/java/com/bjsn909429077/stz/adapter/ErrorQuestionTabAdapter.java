package com.bjsn909429077.stz.adapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/14 22:28
 **/
public class ErrorQuestionTabAdapter extends FragmentStatePagerAdapter {
    private List<String> list;
    private List<Fragment> fragments;

    public ErrorQuestionTabAdapter(@NonNull @NotNull FragmentManager fm,List<String> list,List<Fragment> fragments) {
        super(fm);
        this.list=list;
        this.fragments=fragments;
    }

    @NonNull
    @NotNull
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
        return list.get(position);
    }
}
