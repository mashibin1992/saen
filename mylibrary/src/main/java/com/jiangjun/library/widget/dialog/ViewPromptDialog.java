package com.jiangjun.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;


/**
 * Created by Administrator姜军 on 2018/3/20.
 */

public class ViewPromptDialog extends Dialog {


    public ViewPromptDialog(Context context) {

        super(context, com.jiangjun.library.R.style.CustomDialog);
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
        window.setWindowAnimations(com.jiangjun.library.R.style.CustomListAlertDialog);


        show();
    }

    public void showPopupWindow() {

        Window window = getWindow();
        // 设置充满整个屏幕
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(com.jiangjun.library.R.style.CustomListAlertDialog);


        show();
    }



    public static class BaseDialogBuilder implements BaseDialogLifecycleObserver {

        private Context mContext;
        private ViewPromptDialog mDialog;
        private boolean mCancelable = true;
        private boolean mCanceledOnTouchOutside = true;
        private final View customView;
        private OnDismissListener listener;


        public BaseDialogBuilder(Context context, int view) {
            this.mContext = context;
            customView = LayoutInflater.from(context).inflate(view, null);
            mDialog = new ViewPromptDialog(mContext);
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
        public BaseDialogBuilder setViewReturnListener(ViewReturnListener viewReturnListener) {

            if (viewReturnListener != null) {
                viewReturnListener.onClick(mDialog,customView);
            }

            return this;
        }


        @SuppressWarnings("unchecked")
        public BaseDialogBuilder setViewListener(int rId, ActionListener actionListener) {
            View text = customView.findViewById(rId);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionListener != null)
                        actionListener.onClick(mDialog, rId);
                }
            });
            return this;
        }




        public BaseDialogBuilder setOnDismissListener(OnDismissListener listener) {
            this.listener = listener;
            return this;
        }

        public BaseDialogBuilder addBaseDialogLifecycleObserver(LifecycleOwner owner) {
            if (owner != null) {
                owner.getLifecycle().addObserver(new BaseDialogLifecycleObserverAdapter(owner, this));
            }
            return this;
        }


        @Override
        public void onStop(LifecycleOwner owner) {

        }

        @Override
        public void onStart(LifecycleOwner owner) {

        }

        @Override
        public void onDestroy(LifecycleOwner owner) {
            destroy();
        }


        /**
         * 移除一些引用
         */
        public void destroy() {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        }


        public ViewPromptDialog create() {



            mDialog.setContentView(customView);
            mDialog.setCancelable(mCancelable);
            mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            if (listener != null) {
                mDialog.setOnDismissListener(listener);
            }

            return mDialog;
        }


    }


    public interface ActionListener {
        void onClick(ViewPromptDialog dialog, int rIds);
    }


    public interface ViewReturnListener {
        void onClick(ViewPromptDialog dialog, View view);
    }
}
