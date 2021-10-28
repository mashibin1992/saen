package com.jiangjun.library.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jiangjun.library.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by LeBron James on 2018/5/19.
 */

public abstract class BaseLazyFragment extends Fragment {
    Unbinder mUnbinder;
    /**
     * 布局id
     */
    public View contentView;

    /**
     * 是否启用懒加载，此属性仅对BaseLazyLoadFragment有效
     */
    private boolean isLazyLoad;
    public Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(setLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, contentView);

        mContext = getActivity();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 如果不是懒加载模式，创建就加载数据
        if (!isLazyLoad) {
            loadData();
        }
    }

    /**
     * 设置加载的布局Id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化操作
     */
    protected abstract void init();

    /**
     * findViewById
     */
    protected View findViewById(int id) {
        return contentView.findViewById(id);
    }

    /**
     * 懒加载数据
     */
    protected abstract void loadData();


    /**
     * 是否启用懒加载，此方法仅对BaseLazyLoadFragment有效
     */
    public void setLazyLoad(boolean lazyLoad) {
        isLazyLoad = lazyLoad;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


    public void showToast(String msg) {
        ToastUtils.Toast(mContext, msg);
    }
}
