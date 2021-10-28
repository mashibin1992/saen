package com.bjsn909429077.stz.ui.study.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.BaseQuestionBean;
import com.bjsn909429077.stz.ui.study.contract.EditNoteContract;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.download.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.Map;

public class EditNoteModel {

    private Context context;
    private EditNoteContract contract;

    public EditNoteModel(Context context, EditNoteContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void addNote(Map<String, Object> map) {
        NovateUtils.getInstance().Post(context, BaseUrl.addNote, map, true,
                new NovateUtils.setRequestReturn<BaseQuestionBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                        contract.onError("onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(BaseQuestionBean response) {
                        ToastUtils.showToast(context, "添加成功");
                        contract.onSuccess(response.getSuccess());
                    }
                });
    }

    public void editNote(Map<String, Object> map) {
        NovateUtils.getInstance().Post(context, BaseUrl.editNote, map, true,
                new NovateUtils.setRequestReturn<BaseQuestionBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                        contract.onError("onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(BaseQuestionBean response) {
                        ToastUtils.showToast(context, "添加成功");
                        contract.onSuccess(response.getSuccess());
                    }
                });
    }
}
