package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AddressBean;
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
public class AddressListAdapter extends BaseQuickAdapter<AddressBean.DataBean, BaseViewHolder> {

    private TextView answer_item_name;
    private TextView answer_item_jd;
    private ProgressBar answer_item_pb;

    public AddressListAdapter(int layoutResId, @Nullable List<AddressBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, AddressBean.DataBean dataBean) {
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_phone = holder.getView(R.id.tv_phone);
        TextView tv_address = holder.getView(R.id.tv_address);
        ImageView iv_select = holder.getView(R.id.iv_select);
        ImageView iv_xiugai = holder.getView(R.id.iv_xiugai);
        TextView tv_delete = holder.getView(R.id.tv_delete);

        if (!TextUtil.isEmpty(dataBean.getName())) {
            tv_name.setText(dataBean.getName());
        }
        if (!TextUtil.isEmpty(dataBean.getMobile())) {
            tv_phone.setText(dataBean.getMobile());
        }
        if (!TextUtil.isEmpty(dataBean.getAddress())) {
            tv_address.setText(dataBean.getAddress());
        }


    }
}
