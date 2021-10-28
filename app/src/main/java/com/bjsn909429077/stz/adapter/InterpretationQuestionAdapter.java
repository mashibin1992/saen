package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.SubjectAnalysisBean;
import com.bjsn909429077.stz.bean.lianXiSubjectAnalysisBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/16 22:07
 **/
public class InterpretationQuestionAdapter extends BaseQuickAdapter<lianXiSubjectAnalysisBean.DataBean.AnswerListBean, BaseViewHolder> {
    private String mMyAnswer = "";

    public InterpretationQuestionAdapter(int layoutResId, @Nullable List<lianXiSubjectAnalysisBean.DataBean.AnswerListBean> data, String myAnswerId) {
        super(layoutResId, data);
        mMyAnswer = myAnswerId;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, lianXiSubjectAnalysisBean.DataBean.AnswerListBean s) {
        ImageView option_number = baseViewHolder.itemView.findViewById(R.id.option_number);
        TextView option_content = baseViewHolder.itemView.findViewById(R.id.option_content);
        option_content.setText(s.getAnswerName());
        if (s.getIsRight() == 1) {
            option_number.setImageResource(R.drawable.answer_radio_checked);
        }
        String[] answerIds = mMyAnswer.split(",");
        for (int i = 0; i < answerIds.length; i++) {
            if (answerIds[i].equals(s.getAnswerId()) && s.getIsRight() == 0) {
                option_number.setImageResource(R.drawable.answer_radio_normal);
            }
        }
    }
}
