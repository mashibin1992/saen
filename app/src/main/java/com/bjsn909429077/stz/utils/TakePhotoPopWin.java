package com.bjsn909429077.stz.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TakePhotoPopWin extends PopupWindow {

    private Context mContext;

    private View view;

    private TextView btn_close;
    private RecyclerView recyclerView;
    private List<CoursePackageInfoBean.DataBean.CourseCouponListBean> mCourseCouponListBeans = new ArrayList<>();
    private OnCouponItemCallback mOnCouponItemCallback;
    private final CourseCouponAdapter mCourseCouponAdapter;


    public TakePhotoPopWin(Context mContext) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.take_photo_pop, null, false);

        btn_close = (TextView) view.findViewById(R.id.btn_close);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCourseCouponAdapter = new CourseCouponAdapter(mContext, mCourseCouponListBeans);
        recyclerView.setAdapter(mCourseCouponAdapter);
        // 取消按钮
        btn_close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });

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

    public void setData(List<CoursePackageInfoBean.DataBean.CourseCouponListBean> courseCouponListBeanList) {
        this.mCourseCouponListBeans.clear();
        this.mCourseCouponListBeans.addAll(courseCouponListBeanList);
        mCourseCouponAdapter.notifyDataSetChanged();
    }


    private class CourseCouponAdapter extends BaseQuickAdapter<CoursePackageInfoBean.DataBean.CourseCouponListBean, BaseViewHolder> {
        public CourseCouponAdapter(Context mContext, List<CoursePackageInfoBean.DataBean.CourseCouponListBean> courseCouponListBeans) {
            super(R.layout.item_yhq, courseCouponListBeans);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageInfoBean.DataBean.CourseCouponListBean courseCouponListBean) {
            TextView tv_price = baseViewHolder.getView(R.id.tv_price);
            TextView tv_title = baseViewHolder.getView(R.id.tv_title);
            TextView tv_date = baseViewHolder.getView(R.id.tv_date);
            TextView tv_yang = baseViewHolder.getView(R.id.tv_yang);
            View line = baseViewHolder.getView(R.id.line);
            TextView tv_receive = baseViewHolder.getView(R.id.tv_receive);
            RelativeLayout rl_bg = baseViewHolder.getView(R.id.rl_bg);
            tv_price.setText(courseCouponListBean.price + "");

            if (courseCouponListBean.isReceive == 1) {
                tv_title.setTextColor(mContext.getResources().getColor(R.color.color_828282));
                tv_yang.setTextColor(mContext.getResources().getColor(R.color.color_828282));
                tv_date.setTextColor(mContext.getResources().getColor(R.color.color_828282));
                tv_receive.setTextColor(mContext.getResources().getColor(R.color.color_828282));
                tv_price.setTextColor(mContext.getResources().getColor(R.color.color_828282));
                line.setBackgroundResource(R.drawable.shape_dotted_line_vertical_black);
                rl_bg.setBackground(mContext.getDrawable(R.drawable.bg_card_course_one_over));
                tv_receive.setText("          ");
            } else {
                tv_receive.setOnClickListener(v -> {
                    if (mOnCouponItemCallback != null) {
                        mOnCouponItemCallback.couponCallback(courseCouponListBean);
                    }
                });
            }
            if (!TextUtil.isEmpty(courseCouponListBean.courseCouponName))
                tv_title.setText(courseCouponListBean.courseCouponName);

            tv_date.setText("有效期" + courseCouponListBean.effectiveDay + "天");


        }
    }

    public interface OnCouponItemCallback {
        void couponCallback(CoursePackageInfoBean.DataBean.CourseCouponListBean data);
    }

    public void setOnCouponItemCallback(OnCouponItemCallback onCouponItemCallback) {
        mOnCouponItemCallback = onCouponItemCallback;
    }
}