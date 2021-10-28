package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.WenDaTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class WenDaTypeAdapter extends BaseQuickAdapter<WenDaTypeBean.DataBean, BaseViewHolder> {


    public WenDaTypeAdapter(int layoutResId, @Nullable List<WenDaTypeBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, WenDaTypeBean.DataBean dataBean) {
        TextView tv_name = holder.getView(R.id.tv_name);

        if (!TextUtil.isEmpty(dataBean.getName())) {
            tv_name.setText(dataBean.getName());
        }
    }
}
