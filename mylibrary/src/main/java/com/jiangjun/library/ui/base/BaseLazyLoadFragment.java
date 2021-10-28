package com.jiangjun.library.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jiangjun.library.utils.RLog;

/**
 * Created by LeBron James on 2018/5/19.
 */

public abstract class BaseLazyLoadFragment extends BaseLazyFragment {
    /**
     * 是否已经初始化结束
     */
    private boolean isPrepare;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLazyLoad(true);
        isPrepare = true;
        RLog.e("Fragment", "This is ------->" + getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 创建时要判断是否已经显示给用户，加载数据
        onVisibleToUser();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 显示状态发生变化
        onVisibleToUser();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 判断是否需要加载数据
     */
    private void onVisibleToUser() {
        // 如果已经初始化完成，并且显示给用户
        if (isPrepare && getUserVisibleHint()) {
            loadData();
        }
    }

}
