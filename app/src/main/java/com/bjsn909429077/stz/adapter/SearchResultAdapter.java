package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.bean.SearchBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: zy
 * @Description:
 * @Date :2021/9/6 17:32
 **/
public class SearchResultAdapter extends BaseQuickAdapter<SearchBean.DataBean, BaseViewHolder> {


    private final Context context;

    public SearchResultAdapter(int layoutResId, @Nullable List<SearchBean.DataBean>data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,SearchBean.DataBean dataBean ) {
        ImageView iv = baseViewHolder.getView(R.id.iv);
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_contnt = baseViewHolder.getView(R.id.tv_contnt);
        TextView tv_pirce = baseViewHolder.getView(R.id.tv_pirce);

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

    }


}
