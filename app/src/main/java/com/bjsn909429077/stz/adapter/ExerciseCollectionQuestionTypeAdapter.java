package com.bjsn909429077.stz.adapter;

import android.widget.CheckBox;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ExerciseCollectionQuestionTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/8 17:43
 **/
public class ExerciseCollectionQuestionTypeAdapter extends BaseQuickAdapter<ExerciseCollectionQuestionTypeBean, BaseViewHolder> {

    private TextView exercise_collection_question_type_tv;
    private CheckBox exercise_collection_question_type_cb;

    public ExerciseCollectionQuestionTypeAdapter(int layoutResId, @Nullable List<ExerciseCollectionQuestionTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExerciseCollectionQuestionTypeBean exerciseCollectionQuestionTypeBean) {
        exercise_collection_question_type_tv = baseViewHolder.itemView.findViewById(R.id.exercise_collection_question_type_tv);
        exercise_collection_question_type_cb = baseViewHolder.itemView.findViewById(R.id.exercise_collection_question_type_cb);
        exercise_collection_question_type_tv.setText(exerciseCollectionQuestionTypeBean.getQuestionType());
        exercise_collection_question_type_cb.setChecked(exerciseCollectionQuestionTypeBean.isChecked());
    }
}