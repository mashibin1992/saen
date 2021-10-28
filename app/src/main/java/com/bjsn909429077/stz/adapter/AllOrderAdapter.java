package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AllOrderBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/20 08:58
 **/
public class AllOrderAdapter extends BaseQuickAdapter<AllOrderBean.DataBean, BaseViewHolder> {
    private Context mContext;

    public AllOrderAdapter(int layoutResId, @Nullable List<AllOrderBean.DataBean> data, Context context) {
        super(layoutResId, data);
        mContext=context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AllOrderBean.DataBean allOrderBean) {
        ImageView order_iv = baseViewHolder.itemView.findViewById(R.id.order_iv);
        TextView order_title = baseViewHolder.itemView.findViewById(R.id.order_title);
        TextView order_time = baseViewHolder.itemView.findViewById(R.id.order_time);
        TextView order_should_pay = baseViewHolder.itemView.findViewById(R.id.order_should_pay);
        TextView order_total_pay = baseViewHolder.itemView.findViewById(R.id.order_total_pay);
        TextView order_gift = baseViewHolder.itemView.findViewById(R.id.order_gift);
        TextView cancel_order = baseViewHolder.itemView.findViewById(R.id.cancel_order);
        TextView go_order = baseViewHolder.itemView.findViewById(R.id.go_order);
        TextView look_logistics = baseViewHolder.itemView.findViewById(R.id.look_logistics);
        Glide.with(mContext).load(allOrderBean.getCoverPath()).into(order_iv);
        order_title.setText(allOrderBean.getOrderName());
        //order_time.setText(allOrderBean.getGiveCourseNames());
        order_should_pay.setText("应付：¥"+allOrderBean.getActuallyPrice());
        if (TextUtils.isEmpty(allOrderBean.getTotalPrice())) {
            order_total_pay.setText("");
        }else {
            order_total_pay.setText("总价：¥"+allOrderBean.getTotalPrice());
        }
        //order_gift.setText(allOrderBean.getOrderName());
        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        go_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        look_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
