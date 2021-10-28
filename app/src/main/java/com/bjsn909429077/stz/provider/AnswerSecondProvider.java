package com.bjsn909429077.stz.provider;

import android.annotation.SuppressLint;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AnswerCardFirstBean;
import com.bjsn909429077.stz.bean.AnswerCardSecondBean;
import com.bjsn909429077.stz.ui.questionbank.activity.AnswerCardActivity;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/9 18:35
 **/
public class AnswerSecondProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.answer_second_item;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        AnswerCardSecondBean secondBean = (AnswerCardSecondBean) baseNode;
        baseViewHolder.setText(R.id.answer_second_item_tv, secondBean.getTitle());
        if (!secondBean.isZuoDa()) {//没做过
            if (secondBean.getSign().equals("1")) {//是否标记 1.是 2.否
                baseViewHolder.setBackgroundResource(R.id.answer_second_item_tv, R.drawable.circle_hollow_e8611f_ffffff);
                baseViewHolder.setTextColor(R.id.answer_second_item_tv, getContext().getResources().getColor(R.color.color_E8611F));
            } else {
                baseViewHolder.setBackgroundResource(R.id.answer_second_item_tv, R.drawable.circle_d6deff);
                baseViewHolder.setTextColor(R.id.answer_second_item_tv, getContext().getResources().getColor(R.color.color_FFFFFF));
            }
        } else {//做了
            baseViewHolder.setBackgroundResource(R.id.answer_second_item_tv, R.drawable.circle_5d7dff);
            baseViewHolder.setTextColor(R.id.answer_second_item_tv, getContext().getResources().getColor(R.color.color_FFFFFF));
        }
        if (AnswerCardActivity.cuoTiList != null && AnswerCardActivity.cuoTiList.size() > 0) {
            for (int i = 0; i < AnswerCardActivity.cuoTiList.size(); i++) {
                if (AnswerCardActivity.cuoTiList.get(i).getSubjectId().equals(secondBean.getSubjectId())){
                    baseViewHolder.setBackgroundResource(R.id.answer_second_item_tv, R.drawable.circle_e84432);
                    baseViewHolder.setTextColor(R.id.answer_second_item_tv, getContext().getResources().getColor(R.color.color_FFFFFF));
                }
            }

        }
    }
}
