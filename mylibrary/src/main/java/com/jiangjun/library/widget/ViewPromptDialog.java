package com.jiangjun.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiangjun.library.R;


/**
 * Created by Administrator姜军 on 2018/3/20.
 * 公共弹窗
 */

public class ViewPromptDialog extends Dialog {


    public ViewPromptDialog(Context context) {

        super(context, R.style.CustomDialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }


    public void showDialog() {

        Window window = getWindow();
        // 设置充满整个屏幕
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width = (int) (dm.widthPixels * 0.85);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.CustomListAlertDialog);


        show();
    }


    public static class BaseDialogBuilder {

        private Context mContext;
        private ViewPromptDialog mDialog;
        private boolean mCancelable = true;
        private boolean mCanceledOnTouchOutside = true;
        private final View customView;


        public BaseDialogBuilder(Context context, int view) {
            this.mContext = context;
            customView = LayoutInflater.from(context).inflate(view, null);
        }

        public Context getBaseContext() {
            return mContext;
        }


        @SuppressWarnings("unchecked")
        public BaseDialogBuilder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        @SuppressWarnings("unchecked")
        public BaseDialogBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }


        @SuppressWarnings("unchecked")
        public BaseDialogBuilder setTextView(int rId, String content) {
            TextView text = customView.findViewById(rId);
            text.setText(content);
            return this;
        }

        @SuppressWarnings("unchecked")
        public BaseDialogBuilder setViewReturnListener(ViewReturnListener  viewReturnListener) {

            if (viewReturnListener!=null){
                viewReturnListener.onClick(customView);
            }

            return this;
        }





        @SuppressWarnings("unchecked")
        public BaseDialogBuilder setTextViewListener(int rId, String content, ActionListener actionListener) {
            TextView text = customView.findViewById(rId);
            text.setText(content);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionListener != null)
                        actionListener.onClick(mDialog, rId);
                }
            });
            return this;
        }


        public ViewPromptDialog create() {

            mDialog = new ViewPromptDialog(mContext);

            mDialog.setContentView(customView);
            mDialog.setCancelable(mCancelable);
            mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);

            return mDialog;
        }


    }


    public interface ActionListener {
        void onClick(ViewPromptDialog dialog, int rIds);
    }



    public interface ViewReturnListener {
        void onClick(View view);
    }
}
