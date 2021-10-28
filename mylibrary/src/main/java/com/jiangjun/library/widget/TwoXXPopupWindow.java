package com.jiangjun.library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiangjun.library.R;


public class TwoXXPopupWindow extends PopupWindow implements View.OnClickListener {
    private final TextView but_1;
    private final TextView but_2;
    private final TextView but_3;
    private final TextView cancel;
    private final View line_view;
    private final View line_view_2;
    private final Context mContext;
    OnChoiceTypeCallback  onCallback ;

    public OnChoiceTypeCallback getOnCallback() {
        return onCallback;
    }

    public void setOnCallback(OnChoiceTypeCallback onCallback) {
        this.onCallback = onCallback;
    }

    public TwoXXPopupWindow(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_two_xx, null);
        but_1 = view.findViewById(R.id.but_1);
        but_2 = view.findViewById(R.id.but_2);
        but_3 = view.findViewById(R.id.but_3);
        cancel = view.findViewById(R.id.cancel);
        line_view = view.findViewById(R.id.line_view);
        line_view_2 = view.findViewById(R.id.line_view_2);

        but_1.setOnClickListener(this);
        but_2.setOnClickListener(this);
        but_3.setOnClickListener(this);
        cancel.setOnClickListener(this);

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
//        this.setAnimationStyle(R.style.take_photo_anim);
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

        setBackgroundAlpha(0.7f);//设置屏幕透明度
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.but_1) {//
            onCallback.choiceType(1);
            setBackgroundAlpha(1f);//设置屏幕透明度
            this.dismiss();

        } else if (i == R.id.but_2) {//
            onCallback.choiceType(2);
            setBackgroundAlpha(1f);//设置屏幕透明度
            this.dismiss();

        } else if (i == R.id.but_3) {//
            onCallback.choiceType(3);
            setBackgroundAlpha(1f);//设置屏幕透明度
            this.dismiss();

        } else if (i == R.id.cancel) {//取消
            this.dismiss();
            setBackgroundAlpha(1f);//设置屏幕透明度

        }
    }

    public void setText(String str1,String str2 ){

        but_1.setText(str1);
        but_2.setText(str2);
        if (TextUtils.isEmpty(str2)) {
            but_2.setVisibility(View.GONE);
            line_view.setVisibility(View.GONE);
        }
    }


    public void setText(String str1,String str2,String str3 ){

        but_1.setText(str1);
        but_2.setText(str2);
        but_3.setText(str3);


        if (!TextUtils.isEmpty(str3)) {
            but_3.setVisibility(View.VISIBLE);
            line_view_2.setVisibility(View.VISIBLE);
        }else {
            but_3.setVisibility(View.GONE);
            line_view_2.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(str2)) {
            but_2.setVisibility(View.VISIBLE);
            line_view_2.setVisibility(View.VISIBLE);
        }else {
            but_2.setVisibility(View.GONE);
            line_view_2.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(str1)) {
            but_1.setVisibility(View.VISIBLE);
            line_view.setVisibility(View.VISIBLE);
        }else {
            but_1.setVisibility(View.GONE);
            line_view.setVisibility(View.GONE);
        }


    }





    public interface OnChoiceTypeCallback{
        void choiceType(int type);
    }








}
