package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.HasBuyListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PurchaseListAdapter extends BaseQuickAdapter<HasBuyListBean.DataBean, BaseViewHolder> {

    private PurchaseListClickListener listener;

    public PurchaseListAdapter(ArrayList<HasBuyListBean.DataBean> strings) {
        super(R.layout.item_purchase, strings);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HasBuyListBean.DataBean s) {
        ProgressBar progressBar = baseViewHolder.getView(R.id.practice_first_pb);
        if (s.effectiveEtime.contains(" ")) {
            baseViewHolder.setText(R.id.tv_end_date, "截止:" + s.effectiveEtime.split(" ")[0]);
        }
        baseViewHolder.setText(R.id.tv_title, s.packageName);
        baseViewHolder.setText(R.id.tv_sub_title, s.studyCourseCount + "/" + s.classHourCount1 + "课时");
        if (s.classHourCount1 != null) {
            progressBar.setMax(s.classHourCount1);
        }
        progressBar.setProgress(s.studyCourseCount);
        ImageLoaderUtils.loadUrl(getContext(),s.coverPath,baseViewHolder.getView(R.id.img_bit));


        //截止日期
        baseViewHolder.getView(R.id.tv_end_date).setOnClickListener(v -> {
            if (listener != null) {
                listener.endDateClick(s);
            }
        });
        //继续学习
        baseViewHolder.getView(R.id.tv_continue).setOnClickListener(v -> {
            if (listener != null) {
                listener.continueClick(s);
            }
        });
        //bitmap
        baseViewHolder.getView(R.id.img_bit).setOnClickListener(v -> {
            if (listener != null) {
                listener.continueClick(s);
            }
        });
        //标题
        baseViewHolder.getView(R.id.tv_title).setOnClickListener(v -> {
            if (listener != null) {
                listener.continueClick(s);
            }
        });


    }

    public interface PurchaseListClickListener {
        void endDateClick(HasBuyListBean.DataBean s);

        void continueClick(HasBuyListBean.DataBean s);
    }

    public void setPurchaseListClickListener(PurchaseListClickListener listener) {
        this.listener = listener;
    }
}
