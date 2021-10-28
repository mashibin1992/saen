package com.bjsn909429077.stz.adapter;

import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {



    public SearchHistoryAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String title) {
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        tv_title.setText(title);
    }
}
