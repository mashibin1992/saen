package com.bjsn909429077.stz.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.HomeKingBean;
import com.bjsn909429077.stz.bean.HotSearchBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class SearchHotAdapter extends BaseQuickAdapter<HotSearchBean.DataBean, BaseViewHolder> {



    public SearchHotAdapter(int layoutResId, @Nullable List<HotSearchBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotSearchBean.DataBean dataBean) {
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        tv_title.setText(dataBean.getName());
    }
}
