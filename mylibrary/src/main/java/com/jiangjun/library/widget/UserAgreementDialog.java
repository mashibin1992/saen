package com.jiangjun.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.jiangjun.library.R;
import com.jiangjun.library.widget.dialog.BaseDialogLifecycleObserver;
import com.jiangjun.library.widget.dialog.BaseDialogLifecycleObserverAdapter;
import com.jiangjun.library.widget.dialog.ViewPromptDialog;


/**
 * Created by Administrator姜军 on 2018/3/20.
 */

public class UserAgreementDialog extends Dialog   implements BaseDialogLifecycleObserver {


    private Context mContext;

    private TextView cancleBtn;
    private View customView;
    private final TextView sure_btn;

    private OnDialogClickListener onDialogClickListener;
    private final TextView text_content;
    private final TextView text;

    private String oneClick = "《用户协议》";//第一个人
    private String twoClick = "《隐私政策》";//第二个人

    private int oneClickSize = oneClick.length();
    private int twoClickSize = twoClick.length();



    public interface OnDialogClickListener {
        void clickListener(String password, int whichBtn);

        void clickUserAgreementListener();
        void clickPrivacyPolicyListener();
    }

    public UserAgreementDialog(Context context, OnDialogClickListener DialogClickListener) {

        super(context, R.style.CustomDialog);

        this.mContext = context;
        this.onDialogClickListener = DialogClickListener;
        customView = LayoutInflater.from(context).inflate(
                R.layout.dialog_user_agreement, null);
        text = customView.findViewById(R.id.text);
        text_content = customView.findViewById(R.id.text_content);
        sure_btn = customView.findViewById(R.id.sure_btn);
        cancleBtn = customView.findViewById(R.id.cancle_btn);
        String content = mContext.getString(R.string.User_Agreement_And_Privacy_Policy);
        SpannableString spannableString = new SpannableString(content);

        int oneTart=content.indexOf(oneClick);
        int twoTart=content.indexOf(twoClick);


        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.clickUserAgreementListener();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.mainColor));//设置颜色
                ds.setUnderlineText(false);//去掉下划线
            }
        }, oneTart, oneTart+oneClickSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (onDialogClickListener != null) {
                    onDialogClickListener.clickPrivacyPolicyListener();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.mainColor));//设置颜色
                ds.setUnderlineText(false);//去掉下划线
            }
        }, twoTart, twoTart+twoClickSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        text_content.setText(spannableString);
        text_content.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(customView);


        cancleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UserAgreementDialog.this.dismiss();
                if (onDialogClickListener != null) {
                    onDialogClickListener.clickListener("", 0);
                }
            }
        });

        sure_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UserAgreementDialog.this.dismiss();
                if (onDialogClickListener != null) {
                    onDialogClickListener.clickListener("", 1);
                }


            }
        });

    }

    public void showDialog() {

        Window window = getWindow();

        // 设置充满整个屏幕
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();

        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);

        lp.width = (int) (dm.widthPixels * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.CustomListAlertDialog);

        setCanceledOnTouchOutside(true);

        show();
    }

    public void HiddenCancel() {
        cancleBtn.setVisibility(View.GONE);
    }

    public void setTextSureBtn(String password) {
        sure_btn.setText(password);
    }

    public void setTextCancleBtn(String password) {
        cancleBtn.setText(password);
    }


    public void setTitleText(String str) {
        text.setText(str);
    }

    public UserAgreementDialog addBaseDialogLifecycleObserver(LifecycleOwner owner) {
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
        if (isShowing()) {dismiss();

        }
    }
}
