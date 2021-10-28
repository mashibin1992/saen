package com.jiangjun.library.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiangjun.library.R;

import java.util.List;


/**
 * Creator JiangJun
 * Created by Administrator on 2019/1/9.
 * Describe 空界面
 */

public class EmptyViewUtils {
    /**
     * 添加空页面
     *
     * @param context
     * @param title   内容
     * @return
     */
    public static View getEmptyView(Context context, String title) {
        View inflate = View.inflate(context, R.layout.view_image_empty, null);
        TextView tv_title = inflate.findViewById(R.id.tv_title);
        tv_title.setText(title);
        return inflate;
    }

    /**
     * 修改刷新状态
     *
     * @param adapter
     * @param swipe_view
     * @param page       当前的 page
     * @param dataList   返回集合
     * @param maxSize    每次最大返回数量
     * @param startPage  page 从0或从1开始
     * @return
     */
    public static int changeRefreshState(BaseQuickAdapter adapter, SwipeRefreshLayout swipe_view, int page, List dataList, int maxSize, int startPage) {

        boolean isLoadEnd = dataList.size() < maxSize;

        if (swipe_view.isRefreshing() || page == startPage) {
            swipe_view.setRefreshing(false);
            adapter.getLoadMoreModule().setEnableLoadMore(true);
            adapter.setNewData(dataList);

        } else {
            adapter.addData(dataList);
        }

        if (isLoadEnd) {
            page++;
            adapter.getLoadMoreModule().loadMoreEnd();

        } else {
            page++;
            adapter.getLoadMoreModule().loadMoreComplete();
        }

        return page;
    }


    /**
     * 修改刷新状态
     *
     * @param context
     * @param adapter
     * @param swipe_view
     * @param page       当前page
     * @param dataList   返回数据
     * @param maxSize    最大返回数量
     * @param startPage  page起点
     * @param empty      空页面提示
     * @return
     */
    public static int changeRefreshState(Context context, BaseQuickAdapter adapter, SwipeRefreshLayout swipe_view, int page, List dataList, int maxSize, int startPage, String empty) {

        boolean isLoadEnd = dataList.size() < maxSize;


        if (swipe_view.isRefreshing() || page == startPage) {
            swipe_view.setRefreshing(false);
            adapter.getLoadMoreModule().setEnableLoadMore(true);
            adapter.setNewData(dataList);

            if (dataList.size() == 0) {
                adapter.setEmptyView(getEmptyView(context, empty));
            }

        } else {
            adapter.addData(dataList);
        }

        if (isLoadEnd) {
            adapter.getLoadMoreModule().loadMoreEnd();

        } else {
            page++;
            adapter.getLoadMoreModule().loadMoreComplete();
        }

        return page;
    }


    /**
     * 修改刷新状态
     *
     * @param adapter
     * @param swipe_view
     * @param dataList   返回集合
     * @param maxSize    每次最大返回数量
     * @param startPage  page 从0或从1开始
     * @return
     */
    public static void changeRefreshState(BaseQuickAdapter adapter, SwipeRefreshLayout swipe_view, List dataList, int maxSize, int startPage) {

        boolean isLoadEnd = dataList.size() < maxSize;

        if (swipe_view.isRefreshing() ) {
            swipe_view.setRefreshing(false);
            adapter.getLoadMoreModule().setEnableLoadMore(true);
            adapter.setList(dataList);

        } else {
            adapter.addData(dataList);
        }

        if (isLoadEnd) {
            adapter.getLoadMoreModule().loadMoreEnd();

        } else {
            adapter.getLoadMoreModule().loadMoreComplete();
        }

    }



}
