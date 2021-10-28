package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.FreeQuestionBankBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/5 21:15
 **/
public class FreeQuestionBankLeftAdapter extends BaseQuickAdapter<FreeQuestionBankBean.DataBean, BaseViewHolder> {

    private TextView free_left_item_name;
    private View free_left_item_view;
    private Context mContext;

    public FreeQuestionBankLeftAdapter(int layoutResId, @Nullable List<FreeQuestionBankBean.DataBean> data,Context context) {
        super(layoutResId, data);
        mContext=context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FreeQuestionBankBean.DataBean freeQuestionBankLeftBean) {
        free_left_item_name = baseViewHolder.itemView.findViewById(R.id.free_left_item_name);
        free_left_item_view = baseViewHolder.itemView.findViewById(R.id.free_left_item_view);
        free_left_item_view.setVisibility(freeQuestionBankLeftBean.isIschecked() ? View.VISIBLE : View.GONE);
        free_left_item_name.setText(freeQuestionBankLeftBean.getFirstName());
        if (freeQuestionBankLeftBean.isIschecked())
            free_left_item_name.setBackgroundColor(mContext.getResources().getColor(R.color.color_F8F8F8));
        else
            free_left_item_name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }
}
