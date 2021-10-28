package com.bjsn909429077.stz.widget;

import android.util.SparseArray;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/24 17:10
 **/
public class FragmentPagerAdapterCompat extends FragmentPagerAdapter {
    private SparseArray<Fragment> fragments;

    public FragmentPagerAdapterCompat(@NonNull @NotNull FragmentManager fm) {
        super(fm);
        fragments = new SparseArray<>(getCount());
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragments.remove(position);
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

}
