package com.bjsn909429077.stz.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 限制 输入框的 长度
 */
public class EditChangedListener implements TextWatcher {

    private static final String TAG = "EditChangedListener";
    private Context context;
    private EditText editText;
    private CharSequence temp;// 监听前的文本
    private int editStart;// 光标开始位置
    private int editEnd;// 光标结束位置
    private TextView textView;
    private int charMaxNum = 0;

    public EditChangedListener(Context context, EditText editText, TextView textView , int maxNum) {
        this.context = context;
        this.editText = editText;
        this.textView = textView;
        this.charMaxNum = maxNum;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i(TAG, "输入文本之前的状态" + s + "start" + start + "count" + count + "after" + after);
        temp = s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "输入文字中的状态" + s + "start" + start + "count" + count + "before" + before);
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i(TAG, "输入文字后的状态" + s);
        editStart = editText.getSelectionStart();
        editEnd = editText.getSelectionEnd();
        if (temp.length() > charMaxNum) {
//            Toast.makeText(context, "您最多输入" + charMaxNum + "个字符", Toast.LENGTH_SHORT).show();
            s.delete(editStart - 1, editEnd);
            editText.setText(s);
            editText.setSelection(editText.getText().toString().length());
        }else{
            textView.setText(temp.length()+"/"+charMaxNum);
        }
    }
}