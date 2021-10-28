package com.bjsn909429077.stz.ui.my.fragment;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.MyAnsweredAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AnsweredBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/22 21:44
 **/
public class UnansweredFragment extends BaseLazyLoadFragment {

    private RecyclerView unanswered_rv;

    @Override
    protected int setLayoutId() {
        return R.layout.unanswered_fragment;
    }

    @Override
    protected void init() {
        unanswered_rv = (RecyclerView) findViewById(R.id.unanswered_rv);
    }

    @Override
    protected void loadData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("isAnswer", 2);//是否回答：0、未回答 1、已回答
        map.put("type", 1);//类型：0、我的收藏 1、我的问答
        map.put("page", 0);
        NovateUtils.getInstance().Post(mContext, BaseUrl.answerList, map, true,
                new NovateUtils.setRequestReturn<AnsweredBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AnsweredBean response) {
                        if (response != null && response.getData() != null) {
                            List<AnsweredBean.DataBean> data = response.getData();
                            MyAnsweredAdapter myAnsweredAdapter = new MyAnsweredAdapter(R.layout.my_answered_adapter, data,getActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            unanswered_rv.setLayoutManager(linearLayoutManager);
                            unanswered_rv.setAdapter(myAnsweredAdapter);
                        }
                    }
                });
    }
}
