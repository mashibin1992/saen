package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.ZjListBean;
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
 * @Date :2021/9/6 17:32
 **/
public class ZjListAdapter extends BaseQuickAdapter<ZjListBean.DataBean, BaseViewHolder> {

    private final Context context;
    private final String name;
    private TextView answer_item_name;
    private TextView answer_item_jd;
    private ProgressBar answer_item_pb;

    public ZjListAdapter(Context context,int layoutResId, @Nullable List<ZjListBean.DataBean> data,String name) {
        super(layoutResId, data);
        this.context = context;
        this.name = name;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder,ZjListBean.DataBean dataBean) {
        TextView tv_item_name = holder.getView(R.id.tv_item_name);
        TextView tv_titme = holder.getView(R.id.tv_titme);
        TextView tv_quest_content = holder.getView(R.id.tv_quest_content);
        ImageView iv = holder.getView(R.id.iv);
        //用户头像
        Glide.with(context).load(dataBean.getAnswerUserHeadPic()).into(iv);


        tv_item_name.setText(name+"回答了问题");
        tv_titme.setText(dataBean.getAnswerTime());
        tv_quest_content.setText(dataBean.getQuestionName());


    }
}
