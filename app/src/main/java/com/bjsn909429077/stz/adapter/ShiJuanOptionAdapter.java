package com.bjsn909429077.stz.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/25 22:17
 **/
public class ShiJuanOptionAdapter extends BaseQuickAdapter<ShiJuanTestBean.DataBean.PaperSubjectListBean.AnswerListBean, BaseViewHolder> {
    public ShiJuanOptionAdapter(int layoutResId, @Nullable List<ShiJuanTestBean.DataBean.PaperSubjectListBean.AnswerListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShiJuanTestBean.DataBean.PaperSubjectListBean.AnswerListBean answerListBean) {
        LinearLayout option_ll = baseViewHolder.itemView.findViewById(R.id.option_ll);
        TextView option_tv1 = baseViewHolder.itemView.findViewById(R.id.option_tv1);
        TextView option_tv2 = baseViewHolder.itemView.findViewById(R.id.option_tv2);
        option_tv2.setText(answerListBean.getAnswerName());
        option_tv1.setText(answerListBean.getIndexes());
        if (answerListBean.isClickJX()) {
            if (answerListBean.getIsRight() == 1) {
                option_ll.setBackground(getContext().getResources().getDrawable(R.drawable.circle_rectangle_e5efff_5));
                option_tv1.setBackground(getContext().getResources().getDrawable(R.drawable.circle_5f7dff));
                option_tv1.setTextColor(getContext().getResources().getColor(R.color.color_FFFFFF));
            }
            /*String[] answerIds = mMyAnswerIds.split(",");
            for (int i = 0; i < answerIds.length; i++) {
                if (answerIds[i].equals(answerListBean.getAnswerId()) && answerListBean.getIsRight() == 0) {
                    option_number_cl.setBackground(mContext.getResources().getDrawable(R.drawable.circle_rectangle_fff3f1_5));
                    option_number.setBackground(mContext.getResources().getDrawable(R.drawable.circle_e84423));
                    option_number.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
                }
            }*/
            if (answerListBean.getIsRight() == 0 && answerListBean.isMyselfIsChoose()) {
                option_ll.setBackground(getContext().getResources().getDrawable(R.drawable.circle_rectangle_fff3f1_5));
                option_tv1.setBackground(getContext().getResources().getDrawable(R.drawable.circle_e84432));
                option_tv1.setTextColor(getContext().getResources().getColor(R.color.color_FFFFFF));
            }
        } else {
            if (answerListBean.isMyselfIsChoose()) {
                option_tv1.setBackground(getContext().getResources().getDrawable(R.drawable.circle_5f7dff));
                option_tv1.setTextColor(getContext().getResources().getColor(R.color.color_FFFFFF));
            } else {
                option_tv1.setBackground(getContext().getResources().getDrawable(R.drawable.circle_hollow_363636_ffffff));
                option_tv1.setTextColor(getContext().getResources().getColor(R.color.color_141414));
            }
        }
    }
}
