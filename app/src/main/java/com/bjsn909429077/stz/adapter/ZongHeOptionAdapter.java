package com.bjsn909429077.stz.adapter;

import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/25 22:53
 **/
public class ZongHeOptionAdapter extends BaseQuickAdapter<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean.AnswerListBean, BaseViewHolder> {
    public ZongHeOptionAdapter(int layoutResId, @Nullable List<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean.AnswerListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean.AnswerListBean answerListBean) {
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

