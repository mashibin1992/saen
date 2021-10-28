package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.ui.address.AddAddressActivity;
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
public class AddressListNewAdapter extends BaseQuickAdapter<AddressBean.DataBean, BaseViewHolder> {

    private final Context context;
    private TextView answer_item_name;
    private TextView answer_item_jd;
    private ProgressBar answer_item_pb;
    private OnItemDeleteListener onItemDeleteListener;
    private OnItemSettingListener onItemSettingListener;

    public AddressListNewAdapter(int layoutResId, @Nullable List<AddressBean.DataBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, AddressBean.DataBean dataBean) {
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_phone = holder.getView(R.id.tv_phone);
        TextView tv_address = holder.getView(R.id.tv_address);
        ImageView iv_select = holder.getView(R.id.iv_select);
        ImageView iv_xiugai = holder.getView(R.id.iv_xiugai);
        TextView tv_delete = holder.getView(R.id.tv_delete);
        LinearLayout linear_address = holder.getView(R.id.linear_address);

        if (!TextUtil.isEmpty(dataBean.getName())) {
            tv_name.setText(dataBean.getName());
        }
        if (!TextUtil.isEmpty(dataBean.getMobile())) {
            tv_phone.setText(dataBean.getMobile());
        }
        if (!TextUtil.isEmpty(dataBean.getAddress())) {
            tv_address.setText(dataBean.getProvince()+dataBean.getCity()+dataBean.getArea()+dataBean.getAddress());
        }
        //iv_select
        if (dataBean.getIsDefault()==1){
            iv_select.setImageResource(R.drawable.icon_select1);
        }else {
            iv_select.setImageResource(R.drawable.ic_select_false);
        }


        iv_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改收货地址
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("from","addressList");
                intent.putExtra("bean",dataBean);
                context.startActivity(intent);
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemDeleteListener!=null){
                    onItemDeleteListener.delete(dataBean.getId());
                }
            }
        });
        linear_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSettingListener!=null){
                    if (dataBean.getIsDefault()==1){
                       // dataBean.setIsDefault(0);
                    }else {
                        dataBean.setIsDefault(1);
                    }
                    //判断当前是否是默认，如果是不可修改
                    onItemSettingListener.setting(dataBean);
                }
            }
        });
    }
    public interface OnItemDeleteListener{
        void delete(int id);
    }
    public void setonItemDeleteListener(OnItemDeleteListener onItemDeleteListener){

        this.onItemDeleteListener = onItemDeleteListener;
    }
    public interface OnItemSettingListener{
        void setting(AddressBean.DataBean dataBean);
    }
    public void setonItemSettingListener(OnItemSettingListener onItemSettingListener){

        this.onItemSettingListener = onItemSettingListener;
    }
}
