package com.bjsn909429077.stz.ui.questionbank.dialogfragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.utils.CalculationUtil;
import com.bjsn909429077.stz.utils.UnitConversionUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @Author: Ke.Chen
 * @Description:交卷的DialogFragment
 * @Date :2021/9/10 11:47
 **/
public class JSQDialogFragment extends DialogFragment implements View.OnClickListener {

    private View view;
    private TextView jsq_input;
    private String str;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        //需要用android.R.id.content这个view
        view = inflater.inflate(R.layout.jsq_dialog_fragment, ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        //window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = UnitConversionUtil.getScreenWidth(getActivity());
        window.setAttributes(lp);
        getDialog().setCanceledOnTouchOutside(false);//设置点击外部不消失
        getDialog().setCancelable(false);//设置点击返回键不消失
        initView();
        return view;
    }

    private void initView() {
        jsq_input = view.findViewById(R.id.jsq_input);
        TextView jsq_7 = view.findViewById(R.id.jsq_7);
        TextView jsq_8 = view.findViewById(R.id.jsq_8);
        TextView jsq_9 = view.findViewById(R.id.jsq_9);
        TextView jsq_percent = view.findViewById(R.id.jsq_percent);
        TextView jsq_del = view.findViewById(R.id.jsq_del);
        TextView jsq_clear = view.findViewById(R.id.jsq_clear);
        TextView jsq_4 = view.findViewById(R.id.jsq_4);
        TextView jsq_5 = view.findViewById(R.id.jsq_5);
        TextView jsq_6 = view.findViewById(R.id.jsq_6);
        TextView jsq_add = view.findViewById(R.id.jsq_add);
        TextView jsq_reduce = view.findViewById(R.id.jsq_reduce);
        TextView jsq_left_brackets = view.findViewById(R.id.jsq_left_brackets);
        TextView jsq_1 = view.findViewById(R.id.jsq_1);
        TextView jsq_2 = view.findViewById(R.id.jsq_2);
        TextView jsq_3 = view.findViewById(R.id.jsq_3);
        TextView jsq_ride = view.findViewById(R.id.jsq_ride);
        TextView jsq_except = view.findViewById(R.id.jsq_except);
        TextView jsq_right_brackets = view.findViewById(R.id.jsq_right_brackets);
        TextView jsq_sq = view.findViewById(R.id.jsq_sq);
        TextView jsq_0 = view.findViewById(R.id.jsq_0);
        TextView jsq_spot = view.findViewById(R.id.jsq_spot);
        TextView jsq_equal = view.findViewById(R.id.jsq_equal);
        jsq_input.setOnClickListener(this::onClick);
        jsq_7.setOnClickListener(this::onClick);
        jsq_8.setOnClickListener(this::onClick);
        jsq_9.setOnClickListener(this::onClick);
        jsq_4.setOnClickListener(this::onClick);
        jsq_5.setOnClickListener(this::onClick);
        jsq_6.setOnClickListener(this::onClick);
        jsq_3.setOnClickListener(this::onClick);
        jsq_2.setOnClickListener(this::onClick);
        jsq_1.setOnClickListener(this::onClick);
        jsq_percent.setOnClickListener(this::onClick);
        jsq_del.setOnClickListener(this::onClick);
        jsq_clear.setOnClickListener(this::onClick);
        jsq_add.setOnClickListener(this::onClick);
        jsq_reduce.setOnClickListener(this::onClick);
        jsq_left_brackets.setOnClickListener(this::onClick);
        jsq_ride.setOnClickListener(this::onClick);
        jsq_except.setOnClickListener(this::onClick);
        jsq_right_brackets.setOnClickListener(this::onClick);
        jsq_sq.setOnClickListener(this::onClick);
        jsq_0.setOnClickListener(this::onClick);
        jsq_spot.setOnClickListener(this::onClick);
        jsq_equal.setOnClickListener(this::onClick);
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        str = jsq_input.getText().toString();
        switch (view.getId()) {
            case R.id.jsq_0:
            case R.id.jsq_1:
            case R.id.jsq_2:
            case R.id.jsq_3:
            case R.id.jsq_4:
            case R.id.jsq_5:
            case R.id.jsq_6:
            case R.id.jsq_7:
            case R.id.jsq_8:
            case R.id.jsq_9:
            case R.id.jsq_add:
            case R.id.jsq_left_brackets:
            case R.id.jsq_right_brackets:
            case R.id.jsq_ride:
            case R.id.jsq_except:
            case R.id.jsq_reduce:
                jsq_input.setText(str + ((TextView) view).getText());
                break;
            case R.id.jsq_percent://百分号
                break;
            case R.id.jsq_sq:
                dismiss();
                break;
            case R.id.jsq_del://后退
                jsq_input.setText(str.substring(0, str.length() - 1));
                break;
            case R.id.jsq_clear:
                jsq_input.setText("");
                break;
            case R.id.jsq_spot://小数点
                jsq_input.setText(str + ".");
                break;
            case R.id.jsq_equal://等于
                try {
                    jsq_input.setText(str + "=" + CalculationUtil.calculate(str));
                } catch (Exception e) {
                    e.printStackTrace();
                    jsq_input.setText("表达式错误!");
                }
                break;
        }
    }

}
