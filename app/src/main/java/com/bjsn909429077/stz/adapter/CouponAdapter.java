package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.CouponBean;
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
public class CouponAdapter extends BaseQuickAdapter<CouponBean.DataBean, BaseViewHolder> {

    private final Context context;
    private boolean isType=false;
    private OnItemClickListener onItemClickListener;

    public CouponAdapter(int layoutResId, @Nullable List<CouponBean.DataBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, CouponBean.DataBean dataBean) {
        TextView tv_money = holder.getView(R.id.tv_money);
        TextView tv_miaoshu = holder.getView(R.id.tv_miaoshu);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_button = holder.getView(R.id.tv_button);
        TextView tv_time = holder.getView(R.id.tv_time);
        LinearLayout linear = holder.getView(R.id.linear);
        LinearLayout linear_item = holder.getView(R.id.linear_item);
        RelativeLayout rely_zhankai = holder.getView(R.id.rely_zhankai);
        ImageView iv_zhankai = holder.getView(R.id.iv_zhankai);
        RelativeLayout rely = holder.getView(R.id.rely);


        tv_money.setText("¥"+dataBean.getPrice());
        tv_time.setText("有效期："+dataBean.getEffectiveBtime()+"-"+dataBean.getEffectiveEtime());
        tv_title.setText(dataBean.getCourseCouponName());
        tv_miaoshu.setText("满"+dataBean.getEnablePrice()+"使用");
        if (dataBean.getIsAvailable()==1){//可用
            tv_button.setText("去使用");
            tv_button.setTextColor(Color.parseColor("#5D7DFF"));
            tv_button.setBackgroundResource(R.drawable.bg_eaeeff_c5);
            rely.setBackgroundResource(R.drawable.bg_5d7dff_c10_2);
            linear_item.setEnabled(true);
        }else {
            tv_button.setText("已过期");
            tv_button.setTextColor(Color.parseColor("#B2B2B2"));
            tv_button.setBackgroundResource(R.drawable.bg_f7f7f7_c5);
            rely.setBackgroundResource(R.drawable.bg_dadbe0_c10_2);
            linear_item.setEnabled(false);
        }


        linear.removeAllViews();
        if (dataBean.getAvailableCoursePackageList()!=null&&dataBean.getAvailableCoursePackageList().size()>0){
            List<String> list = dataBean.getAvailableCoursePackageList();
            for (int i = 0; i <list.size() ; i++) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_text, null);
                textView.setText(list.get(i));
                linear.addView(textView);
            }
        }
        if (dataBean.getAvailableCoursePackageList()!=null&&dataBean.getAvailableCoursePackageList().size()<2){
            rely_zhankai.setVisibility(View.GONE);
        }else {
            rely_zhankai.setVisibility(View.VISIBLE);
        }
        rely_zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isType){
                    //展开
                    iv_zhankai.setBackgroundResource(R.drawable.icon_up);
                }else {
                    //收起
                    iv_zhankai.setBackgroundResource(R.drawable.icon_down);
                }
            }
        });
        if (dataBean.getIsAvailable()==1){//可用
            tv_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onClick(dataBean);
                    }
                }
            });
        }

    }
    public interface OnItemClickListener{
        void onClick(CouponBean.DataBean dataBean);
    }
    public void setonItemListener(OnItemClickListener onItemClickListener){


        this.onItemClickListener = onItemClickListener;
    }

}
