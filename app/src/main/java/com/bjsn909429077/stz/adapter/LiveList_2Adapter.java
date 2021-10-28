package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AllLiveListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LiveList_2Adapter extends BaseQuickAdapter<AllLiveListBean.DataBean, BaseViewHolder> {

    private final BaseLazyLoadFragment noteFragment;
    private OnInputBtnClickListener listener;
    private OnSleepPlayClickListener listener3;
    private OnBackPlayClickListener listener2;
    private boolean isShowButton = true;
    private OnPurchaseClickListener listener4;
    private OnYuYueClickListener listener5;

    public LiveList_2Adapter(int layout, ArrayList<AllLiveListBean.DataBean> strings, BaseLazyLoadFragment noteFragment) {
        super(layout, strings);
        this.noteFragment = noteFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AllLiveListBean.DataBean s) {
        ImageView img_top = baseViewHolder.getView(R.id.img_top);
        ImageView img_user_header = baseViewHolder.getView(R.id.img_user_header);
        TextView tv_user_name = baseViewHolder.getView(R.id.tv_user_name);
        TextView tv_date = baseViewHolder.getView(R.id.tv_date);
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);

        LinearLayout ll_price = baseViewHolder.getView(R.id.ll_price);
        TextView tv_money = baseViewHolder.getView(R.id.tv_money);
        //头像下面的名称
        TextView tv_occupation = baseViewHolder.getView(R.id.tv_occupation);

        TextView tv_input = baseViewHolder.getView(R.id.tv_input);//进入
        TextView tv_sleep_play = baseViewHolder.getView(R.id.tv_sleep_play);//带开播
        TextView tv_back_play = baseViewHolder.getView(R.id.tv_back_play);//回放
        TextView tv_purchase = baseViewHolder.getView(R.id.tv_purchase);//购买
        TextView tv_yuyue = baseViewHolder.getView(R.id.tv_yuyue);//预约

        tv_input.setVisibility(View.GONE);
        tv_sleep_play.setVisibility(View.GONE);
        tv_back_play.setVisibility(View.GONE);
        tv_purchase.setVisibility(View.GONE);
        tv_yuyue.setVisibility(View.GONE);

        if (s.isFree == 0) {
            tv_money.setText("免费");
//            if (isShowButton) {
//                if (s.liveState == 0) {//带开播
//                    tv_sleep_play.setVisibility(View.VISIBLE);
//                    tv_input.setVisibility(View.GONE);
//                }
//                if (s.liveState == 1) {//直播中
//                    tv_sleep_play.setVisibility(View.GONE);
//                    tv_input.setVisibility(View.VISIBLE);
//                }
//            } else {//回放
//                tv_back_play.setVisibility(View.VISIBLE);
//            }
        }
        if (s.isFree == 1) {
            tv_money.setText(s.price);
            tv_purchase.setVisibility(View.VISIBLE);
        }
        if (s.type == 1) {//1、进入 2、预约 3、购买 4、待开播 5、回放
//            tv_botton.setText("进入");
            tv_input.setVisibility(View.VISIBLE);
        } else if (s.type == 2) {
//            tv_botton.setText("预约");
            tv_yuyue.setVisibility(View.VISIBLE);
        } else if (s.type == 3) {
//            tv_botton.setText("购买");
            tv_purchase.setVisibility(View.VISIBLE);
        } else if (s.type == 4) {
//            tv_botton.setText("待开播");
            tv_sleep_play.setVisibility(View.VISIBLE);
        } else if (s.type == 5) {
//            tv_botton.setText("回放");
            tv_back_play.setVisibility(View.VISIBLE);
        }

        if (!TextUtil.isEmpty(s.name))
            tv_title.setText(s.name);
        if (!TextUtil.isEmpty(s.liveTime))
            tv_date.setText(s.liveTime);
        if (!TextUtil.isEmpty(s.typeName))
            tv_occupation.setText(s.typeName);
        if (!TextUtil.isEmpty(s.teacherName))
            tv_user_name.setText(s.teacherName);
        if (!TextUtil.isEmpty(s.teacherHeaderPath))
            ImageLoaderUtils.loadUrl(getContext(), s.teacherHeaderPath, R.drawable.icon_home_live, img_user_header);

        tv_input.setOnClickListener(v -> {
            if (listener != null)
                listener.inputBtnClick(s, baseViewHolder.getLayoutPosition());
        });

        tv_sleep_play.setOnClickListener(v -> {
            if (listener != null)
                listener3.sleepPlayClick(s, baseViewHolder.getLayoutPosition());
        });

        tv_back_play.setOnClickListener(v -> {
            if (listener2 != null)
                listener2.backPlayClick(s, baseViewHolder.getLayoutPosition());
        });

        tv_purchase.setOnClickListener(v -> {
            if (listener4 != null)
                listener4.purchaseClick(s, baseViewHolder.getLayoutPosition());
        });

        tv_yuyue.setOnClickListener(v -> {
            if (listener5 != null)
                listener5.yuYueClick(s, baseViewHolder.getLayoutPosition());
        });
    }

    public interface OnInputBtnClickListener {
        void inputBtnClick(AllLiveListBean.DataBean dataBean, int position);
    }

    public void setOnInputBtnClickListener(OnInputBtnClickListener listener) {
        this.listener = listener;
    }

    public interface OnBackPlayClickListener {
        void backPlayClick(AllLiveListBean.DataBean dataBean, int position);
    }

    public void setOnBackPlayClickListener(OnBackPlayClickListener listener) {
        this.listener2 = listener;
    }

    public interface OnSleepPlayClickListener {
        void sleepPlayClick(AllLiveListBean.DataBean dataBean, int position);
    }

    public void setOnSleepPlayClickListener(OnSleepPlayClickListener listener) {
        this.listener3 = listener;
    }

    public void setIsShowButton(boolean isShowButton) {
        this.isShowButton = isShowButton;
    }


    public void setOnPurchaseClickListener(OnPurchaseClickListener listener) {
        this.listener4 = listener;
    }


    public interface OnPurchaseClickListener {
        void purchaseClick(AllLiveListBean.DataBean dataBean, int position);
    }


    public void setYuYueClickListener(OnYuYueClickListener listener) {
        this.listener5 = listener;
    }


    public interface OnYuYueClickListener {
        void yuYueClick(AllLiveListBean.DataBean dataBean, int position);
    }
}
