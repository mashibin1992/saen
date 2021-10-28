package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class HomePageFragmentAdapter extends BaseQuickAdapter<HomeRecommendListBean.DataBean, BaseViewHolder> {


    @Nullable
    private final List<HomeRecommendListBean.DataBean> data;
    private final Context context;

    public HomePageFragmentAdapter(int layoutResId, @Nullable List<HomeRecommendListBean.DataBean>data, Context context) {
        super(layoutResId, data);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,HomeRecommendListBean.DataBean dataBean ) {
        ImageView iv = baseViewHolder.getView(R.id.iv);
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_contnt = baseViewHolder.getView(R.id.tv_contnt);
        TextView tv_pirce = baseViewHolder.getView(R.id.tv_pirce);
        View view = baseViewHolder.getView(R.id.view);

        if (!TextUtils.isEmpty(dataBean.getCoverPath())){
            Glide.with(context).load(dataBean.getCoverPath()).into(iv);
        }
        if (!TextUtils.isEmpty(dataBean.getCourseName())){
            tv_title.setText(dataBean.getCourseName());
        }
        if (!TextUtils.isEmpty(dataBean.getCourseSecondName())){
            tv_contnt.setText(dataBean.getCourseSecondName());
        }

        if (dataBean.getIsFree()==1){
            tv_pirce.setText("¥"+dataBean.getDiscountPrice()+"");

        }else {
            tv_pirce.setText("免费");
        }
        if (baseViewHolder.getAdapterPosition()==data.size()-1){
            view.setVisibility(View.INVISIBLE);
        }else {
            view.setVisibility(View.VISIBLE);
        }

    }


}
