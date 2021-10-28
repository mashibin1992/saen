package com.bjsn909429077.stz.adapter;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AnswerListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class AnswerAdapter extends BaseQuickAdapter<AnswerListBean.DataBean, BaseViewHolder> {

    private TextView answer_item_name;
    private TextView answer_item_jd;
    private ProgressBar answer_item_pb;

    public AnswerAdapter(int layoutResId, @Nullable List<AnswerListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AnswerListBean.DataBean answerBean) {
        answer_item_name = baseViewHolder.itemView.findViewById(R.id.answer_item_name);
        answer_item_jd = baseViewHolder.itemView.findViewById(R.id.answer_item_jd);
        answer_item_pb = baseViewHolder.itemView.findViewById(R.id.answer_item_pb);
        answer_item_name.setText(answerBean.getTestPaperName());
        answer_item_jd.setText(answerBean.getCompleteNumber() + "/" + answerBean.getCountNumber());
        answer_item_pb.setMax(Integer.parseInt(answerBean.getCountNumber()));
        answer_item_pb.setProgress(Integer.parseInt(answerBean.getCompleteNumber()));
    }
}
