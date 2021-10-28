package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.SearchWenDaBean;
import com.bjsn909429077.stz.bean.WdListBean;
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
public class WendaAdapter extends BaseQuickAdapter<WdListBean.DataBean, BaseViewHolder> {


    @Nullable
    private final List<WdListBean.DataBean> data;
    private final Context context;

    public WendaAdapter(int layoutResId, @Nullable List<WdListBean.DataBean>data, Context context) {
        super(layoutResId, data);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,WdListBean.DataBean dataBean ) {
        View view = baseViewHolder.getView(R.id.view);
        if (baseViewHolder.getAdapterPosition()==0){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
        ImageView iv = baseViewHolder.getView(R.id.iv);
        ImageView iv1 = baseViewHolder.getView(R.id.iv1);
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_name = baseViewHolder.getView(R.id.tv_name);
        TextView tv_time_count = baseViewHolder.getView(R.id.tv_time_count);
        LinearLayout linear_img = baseViewHolder.getView(R.id.linear_img);
        //用户头像
        Glide.with(context).load(dataBean.getCustomerHeadPic()).into(iv);
        //解答老师头像
        Glide.with(context).load(dataBean.getAnswerUserHeadPic()).into(iv1);
        tv_title.setText(dataBean.getQuestionName());
        tv_name.setText(dataBean.getAnswerUserNickName());

        tv_time_count.setText(dataBean.getReadCount()+"人阅读"+" ｜"+dataBean.getAnswerTime());


        if (dataBean.getQuestionPics()!=null&&dataBean.getQuestionPics().size()>0){
            linear_img.setVisibility(View.VISIBLE);
            ImageView img_1 = baseViewHolder.getView(R.id.img_1);
            ImageView img_2 = baseViewHolder.getView(R.id.img_2);
            ImageView img_3 = baseViewHolder.getView(R.id.img_3);
            if (dataBean.getQuestionPics().size()==1){
                Glide.with(context).load(dataBean.getQuestionPics().get(0)).into(img_1);
                img_2.setVisibility(View.INVISIBLE);
                img_3.setVisibility(View.INVISIBLE);
            }else  if (dataBean.getQuestionPics().size()==2){
                Glide.with(context).load(dataBean.getQuestionPics().get(0)).into(img_1);
                Glide.with(context).load(dataBean.getQuestionPics().get(1)).into(img_2);
                img_3.setVisibility(View.INVISIBLE);
            }else  if (dataBean.getQuestionPics().size()==3){
                Glide.with(context).load(dataBean.getQuestionPics().get(0)).into(img_1);
                Glide.with(context).load(dataBean.getQuestionPics().get(1)).into(img_2);
                Glide.with(context).load(dataBean.getQuestionPics().get(2)).into(img_3);
            }


        }else {
            linear_img.setVisibility(View.GONE);
        }
        View view1 = baseViewHolder.getView(R.id.view1);
        if (baseViewHolder.getAdapterPosition()==data.size()-1){
            view1.setVisibility(View.GONE);
        }else {
            view1.setVisibility(View.VISIBLE);
        }
    }


}
