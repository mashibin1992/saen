package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.MyPointBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/17 21:13
 **/
public class MyPointsAdaapter extends BaseQuickAdapter<MyPointBean.DataBean.PointChildListBean, BaseViewHolder> {
    private Context mContext;
    public MyPointsAdaapter(Context context,int layoutResId, @Nullable List<MyPointBean.DataBean.PointChildListBean> data) {
        super(layoutResId, data);
        mContext=context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyPointBean.DataBean.PointChildListBean dataBean) {
        TextView my_point_item_name = baseViewHolder.itemView.findViewById(R.id.my_point_item_name);
        TextView my_point_item_date = baseViewHolder.itemView.findViewById(R.id.my_point_item_date);
        TextView my_point_item_number = baseViewHolder.itemView.findViewById(R.id.my_point_item_number);
        if (dataBean.getType() == 1)
            my_point_item_name.setText("课程赠送");
        else
            my_point_item_name.setText("课程消费");
        my_point_item_date.setText(dataBean.getIntime());
        if (dataBean.getStatus() == 1)
            my_point_item_number.setText("+" + dataBean.getPointValue());
        else {
            my_point_item_number.setText("-" + dataBean.getPointValue());
            my_point_item_number.setTextColor(mContext.getResources().getColor(R.color.color_D65B70));
        }
    }
}
