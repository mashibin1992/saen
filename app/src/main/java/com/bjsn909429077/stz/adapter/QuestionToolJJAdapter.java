package com.bjsn909429077.stz.adapter;

import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.QuestionToolJjBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/10 18:30
 **/
public class QuestionToolJJAdapter extends BaseQuickAdapter<QuestionToolJjBean, BaseViewHolder> {

    public QuestionToolJJAdapter(int layoutResId, @Nullable List<QuestionToolJjBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionToolJjBean questionToolJjBean) {
        TextView question_test_item_type = baseViewHolder.itemView.findViewById(R.id.question_test_item_type);
        TextView question_test_item_score = baseViewHolder.itemView.findViewById(R.id.question_test_item_score);
        question_test_item_type.setText(questionToolJjBean.getSubjectTitle());
        question_test_item_score.setText(questionToolJjBean.getSubjectScore());
    }
}
