package com.bjsn909429077.stz.adapter;

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
 * @Date :2021/9/25 22:57
 **/
public class ZongHeShiJuanOptionAdapter extends BaseQuickAdapter<ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean.AnswerListBean, BaseViewHolder> {
    public ZongHeShiJuanOptionAdapter(int layoutResId, @Nullable List<ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean.AnswerListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean.AnswerListBean answerListBean) {
        TextView option_tv1=baseViewHolder.itemView.findViewById(R.id.option_tv1);
        TextView option_tv2=baseViewHolder.itemView.findViewById(R.id.option_tv2);
        option_tv2.setText(answerListBean.getAnswerName());
        option_tv1.setText(answerListBean.getIndexes());
        if (answerListBean.isMyselfIsChoose()){
            option_tv1.setBackground(getContext().getResources().getDrawable(R.drawable.circle_5f7dff));
            option_tv1.setTextColor(getContext().getResources().getColor(R.color.color_FFFFFF));
        }else {
            option_tv1.setBackground(getContext().getResources().getDrawable(R.drawable.circle_hollow_363636_ffffff));
            option_tv1.setTextColor(getContext().getResources().getColor(R.color.color_141414));
        }
    }
}

