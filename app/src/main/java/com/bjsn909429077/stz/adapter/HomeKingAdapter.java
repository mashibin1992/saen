package com.bjsn909429077.stz.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.HomeKingBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class HomeKingAdapter extends BaseQuickAdapter<HomeKingBean.DataBean, BaseViewHolder> {



    public HomeKingAdapter(int layoutResId, @Nullable List<HomeKingBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeKingBean.DataBean dataBean) {
        ImageView iv_1 = baseViewHolder.getView(R.id.iv_1);
        TextView tv_1 = baseViewHolder.getView(R.id.tv_1);
        if (!TextUtils.isEmpty(dataBean.getIcon())){
            ImageLoaderUtils.loadUrl(getContext(),dataBean.getIcon(),iv_1);
        }
        if (!TextUtils.isEmpty(dataBean.getName())){
            tv_1.setText(dataBean.getName());
        }

    }
}
