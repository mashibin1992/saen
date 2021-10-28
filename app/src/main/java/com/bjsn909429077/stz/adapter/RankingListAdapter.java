package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.RankingListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/5 22:50
 **/
public class RankingListAdapter extends BaseQuickAdapter<RankingListBean.DataBean, BaseViewHolder> {
    private Context mContext;
    private TextView ranking_item_number;
    private TextView ranking_item_name;
    private TextView ranking_item_desc;
    private ImageView ranking_item_photo;

    public RankingListAdapter(int layoutResId, @Nullable List<RankingListBean.DataBean> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RankingListBean.DataBean rankingListBean) {
        ranking_item_number = baseViewHolder.itemView.findViewById(R.id.ranking_item_number);
        ranking_item_name = baseViewHolder.itemView.findViewById(R.id.ranking_item_name);
        ranking_item_desc = baseViewHolder.itemView.findViewById(R.id.ranking_item_desc);
        ranking_item_photo = baseViewHolder.itemView.findViewById(R.id.ranking_item_photo);
        ranking_item_number.setText(rankingListBean.getNumber() + "");
        ranking_item_name.setText(rankingListBean.getUserName());
        ranking_item_desc.setText("做题" + rankingListBean.getQuestionsNumber() + "道");
        ImageLoaderUtils.loadCircleCropUrl(mContext,rankingListBean.getUserHead(),ranking_item_photo);
    }
}
