package com.bjsn909429077.stz.adapter;

import android.text.Html;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.MyAnswerBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StudyAnswerAdapter extends BaseQuickAdapter<MyAnswerBean.DataBean, BaseViewHolder> {
    private final BaseLazyLoadFragment answerFragment;
    private OnAnswerItemClickListener listener;

    public StudyAnswerAdapter(ArrayList<MyAnswerBean.DataBean> strings, BaseLazyLoadFragment answerFragment) {
        super(R.layout.item_answer, strings);
        this.answerFragment = answerFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyAnswerBean.DataBean s) {

        baseViewHolder.setText(R.id.tv_answer, s.questionContent);
        if (s.answerContent != null) {
            if (s.answerContent.contains("<")) {
                baseViewHolder.setText(R.id.tv_result, Html.fromHtml(s.answerContent));
            } else {
                baseViewHolder.setText(R.id.tv_result, s.answerContent);
            }
        }
        baseViewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.answerClick(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnAnswerItemClickListener {
        void answerClick(MyAnswerBean.DataBean dataBean, int position);
    }

    public void setAnswerItemClickListener(OnAnswerItemClickListener listener) {
        this.listener = listener;
    }
}
