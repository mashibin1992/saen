package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.SubjectAnalysisBean;
import com.bjsn909429077.stz.bean.lianXiSubjectAnalysisBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/16 14:45
 **/
public class ExerciseAnalysisAdapter extends BaseQuickAdapter<lianXiSubjectAnalysisBean.DataBean.AnswerListBean, BaseViewHolder> {

    private TextView option_number;
    private TextView option_content;
    private ConstraintLayout option_number_cl;
    private Context mContext;
    private String mMyAnswerIds = "";//回答的答案选项ID 多个逗号分割

    public ExerciseAnalysisAdapter(Context context, int layoutResId, @Nullable List<lianXiSubjectAnalysisBean.DataBean.AnswerListBean> data, String myAnswerIds) {
        super(layoutResId, data);
        mContext = context;
        mMyAnswerIds = myAnswerIds;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, lianXiSubjectAnalysisBean.DataBean.AnswerListBean answerListBean) {
        option_number_cl = baseViewHolder.findView(R.id.option_number_cl);
        option_number = baseViewHolder.findView(R.id.option_number);
        option_content = baseViewHolder.findView(R.id.option_content);
        option_number.setText(answerListBean.getIndexes());
        option_content.setText(answerListBean.getAnswerName());
        if (answerListBean.getIsRight() == 1) {
            option_number_cl.setBackground(mContext.getResources().getDrawable(R.drawable.circle_rectangle_e5efff_5));
            option_number.setBackground(mContext.getResources().getDrawable(R.drawable.circle_3178ee));
            option_number.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
        }
        String[] answerIds = mMyAnswerIds.split(",");
        for (int i = 0; i < answerIds.length; i++) {
            if (answerIds[i].equals(answerListBean.getAnswerId()) && answerListBean.getIsRight() == 0) {
                option_number_cl.setBackground(mContext.getResources().getDrawable(R.drawable.circle_rectangle_fff3f1_5));
                option_number.setBackground(mContext.getResources().getDrawable(R.drawable.circle_e84423));
                option_number.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
            }
        }
    }
}
