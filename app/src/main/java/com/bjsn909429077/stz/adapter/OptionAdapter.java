package com.bjsn909429077.stz.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/25 21:30
 **/
public class OptionAdapter extends BaseQuickAdapter<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.AnswerListBean, BaseViewHolder> {
    public OptionAdapter(int layoutResId, @Nullable List<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.AnswerListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.AnswerListBean answerListBean) {
        LinearLayout option_ll = baseViewHolder.itemView.findViewById(R.id.option_ll);
        TextView option_tv1 = baseViewHolder.itemView.findViewById(R.id.option_tv1);
        TextView option_tv2 = baseViewHolder.itemView.findViewById(R.id.option_tv2);
        ImageView option_iv = baseViewHolder.itemView.findViewById(R.id.option_iv);
        option_tv1.setText(answerListBean.getIndexes());
        if (TextUtils.isEmpty(answerListBean.getAnswerPic())) {
            option_tv2.setVisibility(View.VISIBLE);
            option_iv.setVisibility(View.GONE);
            option_tv2.setText(answerListBean.getAnswerName());
        } else {
            option_tv2.setVisibility(View.GONE);
            option_iv.setVisibility(View.VISIBLE);
            ImageLoaderUtils.loadUrl(getContext(), answerListBean.getAnswerPic(), option_iv);
        }
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
