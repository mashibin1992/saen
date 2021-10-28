package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.LiveListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LiveListAdapter extends BaseQuickAdapter<LiveListBean.DataBean, BaseViewHolder> {

    private OnIntoButtonClickListener onIntoButtonClickListener;
    private OnWaitButtonClickListener onWaitButtonClickListener;
    private OnPlayButtonClickListener onPlayButtonClickListener;

    public LiveListAdapter(ArrayList<LiveListBean.DataBean> strings) {
        super(R.layout.item_live_list, strings);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LiveListBean.DataBean s) {
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_date_1 = baseViewHolder.getView(R.id.tv_date_1);
        TextView tv_date_2 = baseViewHolder.getView(R.id.tv_date_2);
        TextView tv_btn_into = baseViewHolder.getView(R.id.tv_btn_into);
        TextView tv_btn_play_back = baseViewHolder.getView(R.id.tv_btn_play_back);
        TextView tv_btn_wait = baseViewHolder.getView(R.id.tv_btn_wait);

        tv_btn_into.setVisibility(View.GONE);
        tv_btn_play_back.setVisibility(View.GONE);
        tv_btn_wait.setVisibility(View.GONE);

        tv_title.setText(s.name);
        tv_date_1.setText(s.playDate);
        tv_date_2.setText("上架时:" + s.plaTime);

        switch (s.state) {
            case 0:
                tv_btn_wait.setVisibility(View.VISIBLE);
                break;
            case 1:
                tv_btn_into.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_btn_play_back.setVisibility(View.VISIBLE);
                break;
        }

        tv_btn_into.setOnClickListener(v -> {
            if (onIntoButtonClickListener != null)
                onIntoButtonClickListener.intoClick(s);
        });
        tv_btn_play_back.setOnClickListener(v -> {
            if (onPlayButtonClickListener != null)
                onPlayButtonClickListener.playClick(s);
        });
        tv_btn_wait.setOnClickListener(v -> {
            if (onWaitButtonClickListener != null)
                onWaitButtonClickListener.waitClick(s);
        });
    }

    public interface OnIntoButtonClickListener {
        void intoClick(LiveListBean.DataBean dataBean);
    }

    public void setOnIntoButtonClickListener(OnIntoButtonClickListener onIntoButtonClickListener) {
        this.onIntoButtonClickListener = onIntoButtonClickListener;
    }

    public interface OnPlayButtonClickListener {
        void playClick(LiveListBean.DataBean dataBean);
    }

    public void setOnPlayButtonClickListener(OnPlayButtonClickListener onPlayButtonClickListener) {
        this.onPlayButtonClickListener = onPlayButtonClickListener;
    }

    public interface OnWaitButtonClickListener {
        void waitClick(LiveListBean.DataBean dataBean);
    }

    public void setOnWaitButtonClickListener(OnWaitButtonClickListener onWaitButtonClickListener) {
        this.onWaitButtonClickListener = onWaitButtonClickListener;
    }

}
