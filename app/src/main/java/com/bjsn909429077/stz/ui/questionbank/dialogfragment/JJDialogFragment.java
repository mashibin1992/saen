package com.bjsn909429077.stz.ui.questionbank.dialogfragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.QuestionToolJjBean;
import com.bjsn909429077.stz.ui.questionbank.activity.AnswerReportActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.bjsn909429077.stz.utils.UnitConversionUtil;
import com.jiangjun.library.utils.AppManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @Author: Ke.Chen
 * @Description:交卷的DialogFragment
 * @Date :2021/9/10 11:47
 **/
public class JJDialogFragment extends DialogFragment {

    private View view;
    private LinearLayout practice_ll;
    private TextView practice_jj;
    private TextView practice_stop;
    private TextView practice_continue;
    private ArrayList<QuestionToolJjBean> questionToolJjBeans;
    private int nodeId;
    private int nodeType;
    private DismissThis dismissThis;
    private boolean isPractice;
    private int testPaperId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        //需要用android.R.id.content这个view
        view = inflater.inflate(R.layout.jj_dialog_fragment, ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        //window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
        lp.width = UnitConversionUtil.dp2px(getActivity(), 240); // 宽度持平
        window.setAttributes(lp);
        getDialog().setCanceledOnTouchOutside(false);//设置点击外部不消失
        getDialog().setCancelable(false);//设置点击返回键不消失
        initView();
        return view;
    }

    private void initView() {
        practice_ll = view.findViewById(R.id.practice_ll);
        practice_jj = view.findViewById(R.id.practice_jj);
        practice_stop = view.findViewById(R.id.practice_stop);
        practice_continue = view.findViewById(R.id.practice_continue);
        //initData();
        initClick();
    }

    private void initData() {
        Bundle bundle = getArguments();
        /*isPractice = bundle.getBoolean("isPractice",false);
        if (isPractice) {
            nodeId = bundle.getInt("nodeId");
            nodeType = bundle.getInt("nodeType");
        }else {
            testPaperId = bundle.getInt("testPaperId");
        }*/
    }

    public void setDismissThis(DismissThis dismissThis) {
        this.dismissThis = dismissThis;
    }

    private void initClick() {
        practice_jj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                dismissThis.jiaoJuan();
            }
        });
        practice_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                dismissThis.stopDo();
            }
        });
        practice_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface DismissThis {
        void stopDo();//停止做题
        void jiaoJuan();//交卷
    }

}
