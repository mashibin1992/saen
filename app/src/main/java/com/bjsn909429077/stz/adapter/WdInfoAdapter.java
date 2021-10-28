package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.WdInfoBean;
import com.bjsn909429077.stz.ui.wenda.QuestionActivity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class WdInfoAdapter extends BaseQuickAdapter<WdInfoBean.DataBean.WdQuestionAnswerDetailListBean, BaseViewHolder> {

    private final Context context;
    @Nullable
    private final List<WdInfoBean.DataBean.WdQuestionAnswerDetailListBean> data;
    private final int count;
    private final int readCount;
    private TextView answer_item_name;
    private TextView answer_item_jd;
    private ProgressBar answer_item_pb;
    private WdInfoAdapter.onBottonClickListener onBottonClickListener;

    public WdInfoAdapter(Context context, int layoutResId, @Nullable List<WdInfoBean.DataBean.WdQuestionAnswerDetailListBean> data, int readCount, int count) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
        this.count = count;//是否解答：0、否 1、是
        this.readCount = readCount;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder,WdInfoBean.DataBean.WdQuestionAnswerDetailListBean dataBean) {
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_name1 = holder.getView(R.id.tv_name1);
        TextView tv_botton = holder.getView(R.id.tv_botton);
        TextView tv_botton1 = holder.getView(R.id.tv_botton1);
        TextView tv_quest_name = holder.getView(R.id.tv_quest_name);
        TextView tv_quest_content = holder.getView(R.id.tv_quest_content);
        TextView tv_content1 = holder.getView(R.id.tv_content1);
        TextView tv_time_count = holder.getView(R.id.tv_time_count);
        TextView tv_time_count1 = holder.getView(R.id.tv_time_count1);
        LinearLayout linear_img = holder.getView(R.id.linear_img);
        LinearLayout linear_img1 = holder.getView(R.id.linear_img1);
        LinearLayout linear = holder.getView(R.id.linear);
        ImageView iv = holder.getView(R.id.iv);
        ImageView iv_1 = holder.getView(R.id.iv_1);



        tv_name.setText(dataBean.getCustomerNickName());
        if (!TextUtils.isEmpty(dataBean.getQuestionName())){
             tv_quest_name.setText(dataBean.getQuestionName());
        }else {
            tv_quest_name.setVisibility(View.GONE);
        }
        tv_quest_content.setText(dataBean.getQuestionDescribe());
        //用户头像
        Glide.with(context).load(dataBean.getCustomerHeadPic()).into(iv);


        if (dataBean.getQuestionPics()!=null&&dataBean.getQuestionPics().size()>0){
            linear_img.setVisibility(View.VISIBLE);
            ImageView img_1 = holder.getView(R.id.img_1);
            ImageView img_2 = holder.getView(R.id.img_2);
            ImageView img_3 = holder.getView(R.id.img_3);
            if (dataBean.getQuestionPics().size()==1){
                Glide.with(context).load(dataBean.getQuestionPics().get(0)).into(img_1);
                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.INVISIBLE);
                img_3.setVisibility(View.INVISIBLE);
            }else  if (dataBean.getQuestionPics().size()==2){
                Glide.with(context).load(dataBean.getQuestionPics().get(0)).into(img_1);
                Glide.with(context).load(dataBean.getQuestionPics().get(1)).into(img_2);
                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.VISIBLE);
                img_3.setVisibility(View.INVISIBLE);
            }else  if (dataBean.getQuestionPics().size()>=3){
                Glide.with(context).load(dataBean.getQuestionPics().get(0)).into(img_1);
                Glide.with(context).load(dataBean.getQuestionPics().get(1)).into(img_2);
                Glide.with(context).load(dataBean.getQuestionPics().get(2)).into(img_3);
                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.VISIBLE);
                img_3.setVisibility(View.VISIBLE);
            }
        }else {
            linear_img.setVisibility(View.GONE);
        }
        if (dataBean.getAnswerPics()!=null&&dataBean.getAnswerPics().size()>0){
            linear_img1.setVisibility(View.VISIBLE);
            ImageView img_11 = holder.getView(R.id.img_11);
            ImageView img_22 = holder.getView(R.id.img_22);
            ImageView img_33 = holder.getView(R.id.img_33);
            if (dataBean.getAnswerPics().size()==1){
                Glide.with(context).load(dataBean.getAnswerPics().get(0)).into(img_11);
                img_11.setVisibility(View.VISIBLE);
                img_22.setVisibility(View.INVISIBLE);
                img_33.setVisibility(View.INVISIBLE);
            }else  if (dataBean.getAnswerPics().size()==2){
                Glide.with(context).load(dataBean.getAnswerPics().get(0)).into(img_11);
                Glide.with(context).load(dataBean.getAnswerPics().get(1)).into(img_22);
                img_11.setVisibility(View.VISIBLE);
                img_22.setVisibility(View.VISIBLE);
                img_33.setVisibility(View.INVISIBLE);
            }else  if (dataBean.getAnswerPics().size()>=3){
                Glide.with(context).load(dataBean.getAnswerPics().get(0)).into(img_11);
                Glide.with(context).load(dataBean.getAnswerPics().get(1)).into(img_22);
                Glide.with(context).load(dataBean.getAnswerPics().get(2)).into(img_33);
                img_11.setVisibility(View.VISIBLE);
                img_22.setVisibility(View.VISIBLE);
                img_33.setVisibility(View.VISIBLE);
            }
        }else {
            linear_img1.setVisibility(View.GONE);
        }
        tv_time_count.setText(readCount+"人阅读"+" ｜"+dataBean.getReleaseTime());

        if (holder.getAdapterPosition()==0){
            if (count==1){
                tv_botton.setText("已解答");
            }else {
                tv_botton.setText("未解答");
            }

        }else {
            tv_botton.setVisibility(View.GONE);
        }
        if (dataBean.getAnswerUserHeadPic()!=null){
            linear.setVisibility(View.VISIBLE);
            Glide.with(context).load(dataBean.getAnswerUserHeadPic()).into(iv_1);
            tv_name1.setText(dataBean.getAnswerUserNickName());
            tv_content1.setText(dataBean.getAnswerContent());
            tv_time_count1.setText(readCount+"人阅读"+" ｜"+dataBean.getAnswerTime());
            if (dataBean.getIsAsk()==1){
                tv_botton1.setVisibility(View.VISIBLE);
                tv_botton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBottonClickListener!=null){
                            StringBuffer wdId = new StringBuffer();
                            for (int i = 0; i <data.size() ; i++) {
                                if (i!=data.size()-1){
                                    wdId.append(data.get(i).getWdId()+",");
                                }else {
                                    wdId.append(data.get(i).getWdId());
                                }
                            }
                            onBottonClickListener.onClick(wdId.toString());
                        }
                    }
                });
            }else {
                tv_botton1.setVisibility(View.GONE);
            }
        }else {
            // 隐藏

            linear.setVisibility(View.GONE);
        }
    }

    public interface onBottonClickListener{
        void onClick(String wdId);
    }
    public void setListener(onBottonClickListener onBottonClickListener){

        this.onBottonClickListener = onBottonClickListener;
    }
}
