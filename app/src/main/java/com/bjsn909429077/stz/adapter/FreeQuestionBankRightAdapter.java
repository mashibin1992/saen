package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.FreeQuestionBankBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/5 21:16
 **/
public class FreeQuestionBankRightAdapter extends BaseQuickAdapter<FreeQuestionBankBean.DataBean.SecondListBean, BaseViewHolder> {

    private TextView free_right_item_name;
    private ImageView free_right_item_icon;
    private Context mContext;

    public FreeQuestionBankRightAdapter(int layoutResId, @Nullable List<FreeQuestionBankBean.DataBean.SecondListBean> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FreeQuestionBankBean.DataBean.SecondListBean freeQuestionBankRightBean) {
        free_right_item_name = baseViewHolder.itemView.findViewById(R.id.free_right_item_name);
        free_right_item_icon = baseViewHolder.itemView.findViewById(R.id.free_right_item_icon);
        free_right_item_name.setText(freeQuestionBankRightBean.getSecondName());
        if (!TextUtil.isEmpty(freeQuestionBankRightBean.getSecondIconUrl()))
            Glide.with(mContext).load(freeQuestionBankRightBean.getSecondIconUrl()).into(free_right_item_icon);
    }
}
