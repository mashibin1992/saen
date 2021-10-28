package com.bjsn909429077.stz.adapter;

import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.TypeErrorQuestionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/15 17:42
 **/
public class TypeErrorQuestionAdapter extends BaseQuickAdapter<TypeErrorQuestionBean.DataBean, BaseViewHolder> {
    public TypeErrorQuestionAdapter(int layoutResId, @Nullable List<TypeErrorQuestionBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TypeErrorQuestionBean.DataBean typeErrorQuestionBean) {
        TextView type_error_title = baseViewHolder.itemView.findViewById(R.id.type_error_title);
        TextView type_error_num = baseViewHolder.itemView.findViewById(R.id.type_error_num);
        type_error_title.setText(typeErrorQuestionBean.getTestPaperName());
        type_error_num.setText("错题"+typeErrorQuestionBean.getErrorNumber()+"个");
    }
}
