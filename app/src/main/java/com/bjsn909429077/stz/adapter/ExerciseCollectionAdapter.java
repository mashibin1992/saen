package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ExerciseCollectionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/8 16:32
 **/
public class ExerciseCollectionAdapter  extends BaseQuickAdapter<ExerciseCollectionBean.DataBean, BaseViewHolder> {

    private TextView exercise_collection_item_title;
    private CheckBox exercise_collection_item_cb;
    private TextView exercise_collection_item_jx;

    public ExerciseCollectionAdapter(int layoutResId, @Nullable List<ExerciseCollectionBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExerciseCollectionBean.DataBean exerciseCollectionBean) {
        exercise_collection_item_title = baseViewHolder.itemView.findViewById(R.id.exercise_collection_item_title);
        exercise_collection_item_cb = baseViewHolder.itemView.findViewById(R.id.exercise_collection_item_cb);
        exercise_collection_item_jx = baseViewHolder.itemView.findViewById(R.id.exercise_collection_item_jx);

        exercise_collection_item_cb.setVisibility(exerciseCollectionBean.isEditState()?View.VISIBLE:View.GONE);
        exercise_collection_item_jx.setVisibility(exerciseCollectionBean.isEditState()?View.GONE:View.VISIBLE);

        exercise_collection_item_title.setText(exerciseCollectionBean.getSubjectTitle());
        exercise_collection_item_cb.setChecked(exerciseCollectionBean.isCancelExerciseCollection());
    }
}
