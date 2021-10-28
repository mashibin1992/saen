package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AnsweredBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/22 21:52
 **/
public class MyAnsweredAdapter extends BaseQuickAdapter<AnsweredBean.DataBean, BaseViewHolder> {
    private Context mContext;
    private ImageView my_answer_item_photo;
    private TextView my_answer_item_content;
    private RecyclerView my_answer_item_rv;
    private ImageView my_answer_item_icon;
    private TextView my_answer_item_name;
    private TextView my_answer_item_date;

    public MyAnsweredAdapter(int layoutResId, @Nullable List<AnsweredBean.DataBean> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AnsweredBean.DataBean dataBean) {
        my_answer_item_photo = baseViewHolder.itemView.findViewById(R.id.my_answer_item_photo);
        my_answer_item_content = baseViewHolder.itemView.findViewById(R.id.my_answer_item_content);
        my_answer_item_rv = baseViewHolder.itemView.findViewById(R.id.my_answer_item_rv);
        my_answer_item_icon = baseViewHolder.itemView.findViewById(R.id.my_answer_item_icon);
        my_answer_item_name = baseViewHolder.itemView.findViewById(R.id.my_answer_item_name);
        my_answer_item_date = baseViewHolder.itemView.findViewById(R.id.my_answer_item_date);

        ImageLoaderUtils.loadCircleCropUrl(mContext,dataBean.getCustomerHeadPic(),my_answer_item_photo);
//        Glide.with(mContext).load(dataBean.getCustomerHeadPic())
//                .asBitmap().centerCrop()
//                .into(new BitmapImageViewTarget(my_answer_item_photo) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        my_answer_item_photo.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
        my_answer_item_content.setText(dataBean.getQuestionName());
        my_answer_item_name.setText(dataBean.getAnswerUserNickName());
        my_answer_item_date.setText(dataBean.getReadCount() + "阅读  | " + dataBean.getAnswerTime());
        ImageLoaderUtils.loadCircleCropUrl(mContext,dataBean.getAnswerUserHeadPic(),my_answer_item_icon);
//        Glide.with(mContext).load(dataBean.getAnswerUserHeadPic())
//                .asBitmap().centerCrop()
//                .into(new BitmapImageViewTarget(my_answer_item_icon) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        my_answer_item_icon.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
        if (dataBean.getQuestionPics().size()>0){
            GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,3);
            my_answer_item_rv.setLayoutManager(gridLayoutManager);
            MyAnsweredPicsAdapter myAnsweredPicsAdapter=new MyAnsweredPicsAdapter(R.layout.my_answered_pics_adapter,dataBean.getQuestionPics(),mContext);
            my_answer_item_rv.setAdapter(myAnsweredPicsAdapter);
        }
    }
}
