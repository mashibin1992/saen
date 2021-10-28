package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.RecentBrowesBean;
import com.bjsn909429077.stz.ui.study.fragment.BrowseFragment;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BrowseListAdapter extends BaseQuickAdapter<RecentBrowesBean.DataBean, BaseViewHolder> {

    private final BrowseFragment browseFragment;
    private PurchaseListClickListener listener;

    public BrowseListAdapter(ArrayList<RecentBrowesBean.DataBean> strings, BrowseFragment browseFragment) {
        super(R.layout.item_browse, strings);
        this.browseFragment = browseFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecentBrowesBean.DataBean s) {
        ImageView img_bit = baseViewHolder.getView(R.id.img_bit);
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_sub_title = baseViewHolder.getView(R.id.tv_sub_title);
        TextView tv_discount = baseViewHolder.getView(R.id.tv_discount);
        TextView tv_go_price = baseViewHolder.getView(R.id.tv_go_price);
        TextView tv_go_study = baseViewHolder.getView(R.id.tv_go_study);

        tv_go_price.setVisibility(View.GONE);
        tv_go_study.setVisibility(View.GONE);

        ImageLoaderUtils.loadUrl(getContext(),s.coverPath,img_bit);
        tv_title.setText(s.packageName);
        tv_sub_title.setText(s.pageageSecondName);

        if (s.isFree == 0) {
            tv_discount.setText("免费");
            tv_go_study.setVisibility(View.VISIBLE);
        } else {
            tv_discount.setText("¥" + s.coursePrice);
            tv_go_price.setVisibility(View.VISIBLE);
        }

        baseViewHolder.itemView.setOnClickListener(v -> {
            if (s.isFree == 0) {
                //去学习
                browseFragment.goStudy(s);
            } else if (s.isFree == 1) {
                //去购买
                browseFragment.goPrice(s);
            }
        });

    }


    public interface PurchaseListClickListener {
        void endDateClick(RecentBrowesBean.DataBean s);

        void continueClick(RecentBrowesBean.DataBean s);
    }

    public void setPurchaseListClickListener(PurchaseListClickListener listener) {
        this.listener = listener;
    }
}
