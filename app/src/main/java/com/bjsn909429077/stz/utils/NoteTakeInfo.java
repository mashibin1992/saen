package com.bjsn909429077.stz.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;

import java.util.ArrayList;
import java.util.List;

public class NoteTakeInfo extends PopupWindow {


    private Context mContext;

    private View view;

    //    private TextView btn_close;
    private TextView tv_info;
    private RecyclerView recyclerView;
    private TakePhotoPopWin.OnCouponItemCallback mOnCouponItemCallback;


    public NoteTakeInfo(Context mContext) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.fragment_handout_photo_pop, null, false);

//        btn_close = (TextView) view.findViewById(R.id.btn_close);
        tv_info = (TextView) view.findViewById(R.id.tv_info);
        // 取消按钮
//        btn_close.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // 销毁弹出框
//                dismiss();
//            }
//        });

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }

    public void setContent(String string) {
        Spanned spanned = Html.fromHtml(string);
        tv_info.setText(spanned);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor, 0, 0, Gravity.BOTTOM);
    }


    public interface OnCouponItemCallback {
        void couponCallback(CoursePackageInfoBean.DataBean.CourseCouponListBean data);
    }

    public void setOnCouponItemCallback(TakePhotoPopWin.OnCouponItemCallback onCouponItemCallback) {
        mOnCouponItemCallback = onCouponItemCallback;
    }
}
