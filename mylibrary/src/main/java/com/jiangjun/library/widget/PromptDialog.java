package com.jiangjun.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
 */

public class PromptDialog extends Dialog {


    private Context context;

    private TextView cancleBtn;
    private View customView;
    private final TextView sure_btn;
    private String prompt;

    private OnDialogClickListener onDialogClickListener;
    private final TextView text_content;
    private final TextView text;

    public interface OnDialogClickListener {
        void clickListener(String password, int whichBtn);
    }

    public PromptDialog(Context context, String prompt, OnDialogClickListener onDialogClickListener) {

        super(context, R.style.CustomDialog);

        this.context = context;
        this.prompt = prompt;
        this.onDialogClickListener = onDialogClickListener;
        customView = LayoutInflater.from(context).inflate(
                R.layout.dialog_prompt, null);
        text = customView.findViewById(R.id.text);
        text_content = customView.findViewById(R.id.text_content);
        sure_btn =  customView.findViewById(R.id.sure_btn);
        cancleBtn =  customView.findViewById(R.id.cancle_btn);

        text.setText(prompt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(customView);


        cancleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PromptDialog.this.dismiss();
                if (onDialogClickListener!=null){
                    onDialogClickListener.clickListener("", 0);
                }
            }
        });

        sure_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PromptDialog.this.dismiss();
                if (onDialogClickListener!=null){
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
    public void HiddenCancel(){
        cancleBtn.setVisibility(View.GONE);
    }

    public void setTextSureBtn(String password){
        sure_btn.setText(password);
    }

    public void setTextCancleBtn(String password){
        cancleBtn.setText(password);
    }

    public void seContent(String content){
        text_content.setVisibility(View.VISIBLE);
        text_content.setText(content);
    }


    public void setTitleText(String str){
        text.setText(str);
    }
}
