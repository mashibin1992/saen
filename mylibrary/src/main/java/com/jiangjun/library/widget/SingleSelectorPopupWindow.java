package com.jiangjun.library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiangjun.library.R;

import java.util.ArrayList;


/**
 * Created by Administrator姜军 on 2018/6/6.
 * 单个选择器
 */

public class SingleSelectorPopupWindow extends PopupWindow implements View.OnClickListener {


    private final Context mContext;
    private onChooseClickListener monChooseClickListener;
    private ArrayList<WheelView.DataBean> list;
    private final TextView pop_text_queren;
    private final TextView pop_text_quxiao;
    private final WheelView wheelView;
    private final View view;


    public SingleSelectorPopupWindow(Context context, ArrayList<WheelView.DataBean> list, SingleSelectorPopupWindow.onChooseClickListener onChooseClickListener) {
        super(context);
        this.mContext = context;
        this.list = list;
        this.monChooseClickListener = onChooseClickListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popupwindow_selector, null);
        pop_text_queren = (TextView) view.findViewById(R.id.pop_text_queren_hw);
        pop_text_quxiao = (TextView) view.findViewById(R.id.pop_text_quxiao_hw);
        wheelView = (WheelView) view.findViewById(R.id.pop_wheelView_sex_hw);
        pop_text_queren.setOnClickListener(this);
        pop_text_quxiao.setOnClickListener(this);

        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow可获得焦点
        this.setFocusable(true);
        //设置PopupWindow可触摸
        this.setTouchable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);//设置屏幕透明度
            }
        });
        // 设置动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }


    public void show(View view) {
        if (list == null) {
            return;
        }

        wheelView.setData(list);
        wheelView.setDefault(0);

        setBackgroundAlpha(0.7f);//设置屏幕透明度
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.pop_text_queren_hw) {
            monChooseClickListener.onChoose(wheelView.getSelectedText(), wheelView.getSelectedObject());
            this.dismiss();
        }
        if (i == R.id.pop_text_quxiao_hw) {
            this.dismiss();
        }

    }

    public void setButtonText(String quxiao, int color_qx, String queren, int color_qr) {
        if (pop_text_quxiao != null) {
            pop_text_quxiao.setText(quxiao);
            pop_text_quxiao.setTextColor(color_qx);
        }
        if (pop_text_queren != null) {
            pop_text_queren.setText(queren);
            pop_text_queren.setTextColor(color_qr);
        }

    }

    public void setWheelViewColor(int SelectedColor) {
        if (wheelView != null) {
            wheelView.setSelectedColor(SelectedColor);
            wheelView.setDefault(0);
        }
    }

    public interface onChooseClickListener {
        void onChoose(String choos,Object bean);
    }
}
